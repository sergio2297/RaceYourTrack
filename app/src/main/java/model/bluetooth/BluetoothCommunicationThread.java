package model.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

import model.carController.CarController;

/**
 * Receives a BluetoothSocket that already has established a connection with another device. Allow to
 * send commands from the app to the Bluetooth module. For receive messages from the Arduino it will
 * be necessary to perform a Thread with a InputStream and use its in.available() method.
 */
public class BluetoothCommunicationThread extends Thread {

    //---- Constants and Definitions ----

    //---- Attributes ----
    private final BluetoothSocket bluetoothSocket;
    private final OutputStream outputStream;

    //---- Constructor ----
    public BluetoothCommunicationThread(final BluetoothSocket socket) {
        bluetoothSocket = socket;

        // Get the output streams
        OutputStream tempOut = null;
        try {
            tempOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(BluetoothCommunicationThread.class.getCanonicalName(), "Error occurred while creating output stream", e);
        }

        outputStream = tempOut;
    }

    //---- Methods ----
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
