package model.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import model.Game;
import model.carController.CarController;
import model.lapCounter.LapCounter;

/**
 * Receives a BluetoothSocket that already has established a connection with another device. Allow to
 * send commands from the app to the Bluetooth module. For receive messages from the Arduino it will
 * be necessary to perform a Thread with a InputStream and use its in.available() method.
 */
public class BluetoothCommunicationThread extends Thread {

    //---- Constants and Definitions ----

    //---- Attributes ----
    private boolean stillConnected = false;
    private final BluetoothSocket bluetoothSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private byte[] buffer;

    //---- Constructor ----
    public BluetoothCommunicationThread(final BluetoothSocket socket) {
        bluetoothSocket = socket;

        // Get the input stream
        InputStream tempIn = null;
        try {
            tempIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e(BluetoothCommunicationThread.class.getCanonicalName(), "Error occurred while creating input stream", e);
        }
        inputStream = tempIn;

        // Get the output stream
        OutputStream tempOut = null;
        try {
            tempOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(BluetoothCommunicationThread.class.getCanonicalName(), "Error occurred while creating output stream", e);
        }

        outputStream = tempOut;
    }

    //---- Methods ----
    @Override
    public void run() {
        buffer = new byte[1024];
        int numBytes; // bytes returned from read()

        LapCounter lapCounter = Game.getInstance().getLapCounter();

        // Keep listening to the InputStream until an exception occurs or the connection is finished.
        stillConnected = true;
        while (stillConnected) {
            try {
                // Read from the InputStream.
                String readMessage = "";
                numBytes = inputStream.read(buffer);
                for(int i = 0; i < numBytes; ++i) {
                    readMessage += (char)((int)buffer[i]);
                }

                if(!lapCounter.isStarted()) {
                    Log.i("", "LapCounter Initialized");
                    lapCounter.initialize(3, true);
                    lapCounter.start();
                } else {
                    lapCounter.checkPassed(readMessage.charAt(0));
                }

            } catch (IOException e) {
                Log.d(BluetoothCommunicationThread.class.getCanonicalName(), "Input stream was disconnected", e);
                break;
            }
        }
    }

    /**
     * Call this from the main activity to send data to the remote device.
     * @throws IOException if the bytes haven't be sent correctly
     */
    public void write(byte[] bytes) throws IOException {
        outputStream.write(bytes);
        outputStream.flush();
    }

    /**
     * Call this method from the main activity to shut down the connection.
     */
    public void closeCommunication() {
        try {
            bluetoothSocket.close();
            stillConnected = false;
        } catch (IOException e) {
            Log.e(BluetoothCommunicationThread.class.getCanonicalName(), "Could not close the connect socket", e);
        }
    }

    /**
     * The method isConnected() will return true if one connection has been established. So is
     * independent of the current state of the connection. For that, we will try to send a empty
     * command, and if we get an Exception it's mean that the connection isn't available no more.
     */
    public boolean isConnected() {
        try {
            outputStream.write(CarController.SYSTEM_END_COMMAND);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
