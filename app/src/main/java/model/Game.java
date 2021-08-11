package model;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import model.bluetooth.BluetoothCommunicationThread;

public class Game {

    //---- Constants and Definitions ----
    private static Game game = null;

    //---- Attributes ----
    private String racerName;
    private int coins;
    private Car selectedCar;

    private BluetoothCommunicationThread communicationThread;
    private boolean errorHasHappenedBefore = false;

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

    public boolean isCarConnected() {
        return communicationThread != null && communicationThread.isConnected();
    }

    public void createCommunicationThread(final BluetoothSocket socket) {
        if(communicationThread != null) {
            communicationThread.closeCommunication();
        }
        communicationThread = new BluetoothCommunicationThread(socket);
        communicationThread.start();
        errorHasHappenedBefore = false;
    }

    public void sendMessageToRcCar(final String msg) {
        try {
            communicationThread.write(msg.getBytes());
        } catch(IOException ex) {
            if(!errorHasHappenedBefore) { // With this, we control don't throw more than one error when a instruction implies more than one command (like press the throttle button)
                errorHasHappenedBefore = true;
                new AppError(AppErrorHandler.CodeErrors.BLUETOOTH_CONNECTION_LOST,
                        RaceYourTrackApplication.getContext().getResources().getString(R.string.lost_bluetooth_connection),
                        RaceYourTrackApplication.getContext());
            }
        }
    }

    public void destroyCommunicationThread() {
        if(communicationThread != null) {
            communicationThread.closeCommunication();
            try {
                communicationThread.join();
            } catch (InterruptedException ex) {
                Log.i(Game.class.getCanonicalName(), "This should never happen");
            }
        }
    }
}
