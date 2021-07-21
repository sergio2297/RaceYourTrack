package model;

import android.bluetooth.BluetoothSocket;

import model.bluetooth.BluetoothCommunicationThread;

public class Game {

    //---- Constants and Definitions ----
    private static Game game = null;

    //---- Attributes ----
    private String racerName;
    private int coins;
    private Car selectedCar;

    private BluetoothCommunicationThread communicationThread;

    //---- Construction ----
    private Game() {
        selectedCar = Car.MUSCLE_CAR;
    }

    public static Game getInstance() {
        if(game == null) {
            game = new Game();
        }
        return game;
    }

    //---- Methods ----
    public Car getSelectedCar() {
        return selectedCar;
    }

    public boolean isCarConnected() { // FIXME: Mejorar para que informe de verdad cuando se esta conectado o no
        return communicationThread != null && communicationThread.isConnected();
    }

    public void createCommunicationThread(final BluetoothSocket socket) {
        if(communicationThread != null) {
            communicationThread.cancel();
        }
        communicationThread = new BluetoothCommunicationThread(socket);
    }

    public void sendMessageToRcCar(final String msg) {
        communicationThread.write(msg.getBytes());
    }

    public void destroyCommunicationThread() {
        if(communicationThread != null && communicationThread.isAlive()) {
            communicationThread.cancel();
        }
    }
}
