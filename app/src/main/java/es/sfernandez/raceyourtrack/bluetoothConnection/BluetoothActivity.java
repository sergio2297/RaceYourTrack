package es.sfernandez.raceyourtrack.bluetoothConnection;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import es.sfernandez.raceyourtrack.MainActivity;
import es.sfernandez.raceyourtrack.R;
import model.Game;
import utils.viewComponents.IndeterminateProgressDialogC;

/**
 * This activity is performance the actions necessaries to establish connection with the rc car.
 * First it will make some previous steps like check if the device supports bluetooth and if it is
 * enabled (if not, ask the user to enable it).
 *
 * After that, it will run the BluetoothTryToConnectThread. This will try to connect the rc car in
 * the paired devices. If it isn't paired, it will try to discover it. If not, the process will
 * failure and the user will be notified of pair the device manually out of the RaceYourTrackApp.
 *
 * If the BluetoothTryToConnectThread find the rc car, it will throw a new Thread which work is
 * establish the connection. BluetoothEstablishConnectionThread will performance the bind between
 * mobile and car. When the connection is established, the work to do with the connection is done
 * by another thread which is referenced by the Game class. This new Thread contains the socket to
 * the rc's bluetooth module, and is used to send messages.
 *
 * Last but not least, when this activity is destroyed can happen two things: success or failure.
 * But, in both cases, the onDestroy() method will leave the state of the system safe, deleting every
 * object which could be still running. Also the activity will return to the specified activity on
 * its creation or to the MainActivity if null.
 *
 * It's very important that the process of establishing the connection runs in threads apart from
 * the main thread, if not the UI will be locked.
 */
public class BluetoothActivity extends AppCompatActivity {

    //---- Constants and Definitions ----
    private final int REQUEST_ENABLE_BLUETOOTH = 1;
    private final String RC_DEVICE_NAME = "RaceYourTrack_Car";
    private final String RC_DEVICE_MAC = "00:21:13:00:53:D6";
    private final String RC_DEVICE_PIN = "7806";

    //---- Attributes ----
    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver receiver;
    private BluetoothDevice rcCarDevice = null;

    private BluetoothTryToConnectThread bluetoothTryToConnectThread;

    private boolean processSuccessful = false;
    private Class targetActivityOnFinish;
    private IndeterminateProgressDialogC progressDialog;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetActivityOnFinish = (Class) getIntent().getSerializableExtra("targetActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get the bluetoothAdapter (the device's bluetooth radio) if exists. Because it's possible
        // that a system hasn't got it.
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            finish();
        }

        // Enable bluetooth if it isn't enabled
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
        } else {
            progressDialog = new IndeterminateProgressDialogC(getResources().getString(R.string.bluetooth_progress_dialog_title),"", () -> {
                bluetoothTryToConnectThread = new BluetoothTryToConnectThread();
                bluetoothTryToConnectThread.start();
            });
            progressDialog.showNow(getSupportFragmentManager(), BluetoothActivity.class.getCanonicalName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK) {
                progressDialog = new IndeterminateProgressDialogC(getResources().getString(R.string.bluetooth_progress_dialog_title),"", () -> {
                    bluetoothTryToConnectThread = new BluetoothTryToConnectThread();
                    bluetoothTryToConnectThread.start();
                });
                progressDialog.showNow(getSupportFragmentManager(), BluetoothActivity.class.getCanonicalName());
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(progressDialog != null && progressDialog.isVisible()) {
            progressDialog.dismiss();
        }

        if(bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        } else if(bluetoothAdapter == null) {
            Toast.makeText(this, "Your device doesn't support Bluetooth", Toast.LENGTH_SHORT).show();
        }

        if(receiver != null) {
            unregisterReceiver(receiver);
        }

        if(rcCarDevice == null) {
            Toast.makeText(this, "It's not possible to find your car", Toast.LENGTH_SHORT).show();
        }

        if(processSuccessful) {
            Game.getInstance().getSoundPlayer().playMotorOnSound();
        }

        if(processSuccessful == false || targetActivityOnFinish == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if(processSuccessful) {
            Intent intent = new Intent(getApplicationContext(), targetActivityOnFinish);
            getApplicationContext().startActivity(intent);
        }
    }


    public class BluetoothTryToConnectThread extends Thread {

        @Override
        public void run() {
            tryToConnectToRcCar();
        }

        /**
         * First try will be find the device in paired devices list. If it isn't there, it will try to
         * discover it. In both cases, it will try to connect immediately.
         */
        private void tryToConnectToRcCar () {
            rcCarDevice = findRcCarDeviceOnPairedDevices();
            if (rcCarDevice != null) {
                connectToRcCar();
            } else {
                progressDialog.setMessage(getResources().getString(R.string.rc_car_not_paired));
                discoverRcCar();
            }
        }

        /**
         * Search the RcCar in the pairedDevices list. If it isn't found it will return null. For looking
         * for it will use the device's name and MAC given like final fields.
         */
        private BluetoothDevice findRcCarDeviceOnPairedDevices() {
            // Get synchronized devices.
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                // Search the RC car device, and try to connect to it
                progressDialog.setMessage(getResources().getString(R.string.looking_for_in_paired_devices));
                Iterator<BluetoothDevice> it = pairedDevices.iterator();
                while (it.hasNext() && rcCarDevice == null) {
                    BluetoothDevice device = it.next();
                    if (device.getName().equals(RC_DEVICE_NAME) && device.getAddress().equals(RC_DEVICE_MAC)) {
                        progressDialog.setMessage(getResources().getString(R.string.founded_in_paired_devices));
                        return device;
                    }
                }
            }
            return null;
        }

        /**
         * Start to discover devices. When rc car is found (known by the device's name and MAC given
         * like final fields) it will try to connect immediately.
         */
        private void discoverRcCar() {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(intent.getAction())) {
                        progressDialog.setMessage(getResources().getString(R.string.discovery_started));
                    } else if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        if (device.getName().equals(RC_DEVICE_NAME) && device.getAddress().equals(RC_DEVICE_MAC)) {
                            progressDialog.setMessage(getResources().getString(R.string.car_discovered));
                            rcCarDevice = device;
                            if (bluetoothAdapter.isDiscovering()) {
                                bluetoothAdapter.cancelDiscovery();
                            }
                            connectToRcCar();
                        }
                    } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
                        progressDialog.setMessage(getResources().getString(R.string.discovery_finished));
                        if (rcCarDevice == null) {
                            finish();
                        }
                    }
                }
            };

            // Register for broadcasts when a device is discovered.
            registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
            registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));

            if (!bluetoothAdapter.startDiscovery()) {
                progressDialog.setMessage(getResources().getString(R.string.impossible_discovering));
            }
        }

        private void connectToRcCar() {
            BluetoothEstablishConnectionThread bluetoothEstablishConnectionThread = new BluetoothEstablishConnectionThread(rcCarDevice);
            bluetoothEstablishConnectionThread.run();
        }
    }


    /**
     * This thread is used for establish the connection between the devices. It's necessary to do this
     * from an apart Thread because the connect() call blocks until the connection is established or failed.
     */
    private class BluetoothEstablishConnectionThread extends Thread {

        //---- Constants and Definitions ----
        /**
         * From android.bluetooth.BluetoothDevice.createInsecureRfcommSocketToServiceRecord():
         *  If you are connecting to a Bluetooth serial board then try using the well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB.
         */
        private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        private final int MAX_NUM_ATTEMPTS = 5;

        //---- Attributes ----
        private final BluetoothSocket bluetoothSocket;

        //---- Constructor ----
        public BluetoothEstablishConnectionThread(final BluetoothDevice device) {
            BluetoothSocket tmp = null;
            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(BluetoothEstablishConnectionThread.class.getCanonicalName(), "Socket's create() method failed", e);
            }
            bluetoothSocket = tmp;
        }

        public void run() {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            progressDialog.setMessage(getResources().getString(R.string.trying_to_connect));
            int attempt = 0;
            while(!bluetoothSocket.isConnected() && attempt < MAX_NUM_ATTEMPTS) {
                try {
                    bluetoothSocket.connect();
                    progressDialog.setMessage(getResources().getString(R.string.connected_to_your_car));
                } catch (IOException connectException) {
                    ++attempt;
                }
            }

            if(attempt >= MAX_NUM_ATTEMPTS) {
                try {
                    bluetoothSocket.close();
                    return;
                } catch (IOException closeException) {
                    Log.e(BluetoothEstablishConnectionThread.class.getCanonicalName(), "Could not close the client socket", closeException);
                }
            }

            // Run another thread that will perform the communication between the device and the app.
            Game.getInstance().createCommunicationThread(bluetoothSocket);
            processSuccessful = true;
            finish();
        }

    }

}
