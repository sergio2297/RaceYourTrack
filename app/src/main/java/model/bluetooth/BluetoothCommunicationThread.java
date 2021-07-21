package model.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Receives a BluetoothSocket that already has established a connection with another device
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
            Log.e("", "Error occurred while creating output stream", e);
        }

        outputStream = tempOut;
    }

    //---- Methods ----
    // Call this from the main activity to send data to the remote device.
    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            Log.e("", "Error occurred while sending data", e);
        }
    }

    // Call this method from the main activity to shut down the connection.
    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            Log.e("", "Could not close the connect socket", e);
        }
    }

    public boolean isConnected() {
        return bluetoothSocket.isConnected(); // FIXME: Control when the device disable bluetooth
    }
}
