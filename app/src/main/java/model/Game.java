package model;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

import db.dao.DaoSettings;
import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import model.bluetooth.BluetoothCommunicationThread;
import model.challenges.Challenge;
import model.lapCounter.LapCounter;
import model.soundPlayer.SoundPlayer;

public class Game {

    //---- Constants and Definitions ----
    private static Game game = null;

    //---- Attributes ----
    private String racerName;
    private int coins;
    private Car selectedCar;

    private Challenge selectedChallenge;
    private boolean isChallengeMode = false;
    private LapCounter lapCounter = new LapCounter();

    private BluetoothCommunicationThread communicationThread;
    private boolean errorHasHappenedBefore = false;

    private SoundPlayer soundPlayer = new SoundPlayer(RaceYourTrackApplication.getContext());

    //---- Construction ----
    private Game() {
        selectedCar = Car.SPORT_CAR;
        loadCoinsFromDB();
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

    public Challenge getSelectedChallenge() {
        return selectedChallenge;
    }

    public void setSelectedChallenge(Challenge selectedChallenge) {
        this.selectedChallenge = selectedChallenge;
    }

    public void setChallengeMode(final boolean challengeMode) {
        this.isChallengeMode = challengeMode;
    }

    public boolean isChallengeMode() {
        return isChallengeMode;
    }

    public LapCounter getLapCounter() {
        return lapCounter;
    }
    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void loadCoinsFromDB() {
        try {
            coins = Integer.parseInt(new DaoSettings().get(DaoSettings.GENERAL_GROUP, DaoSettings.CURRENT_COINS_VAR).getValue());
        } catch (NumberFormatException ex) {
            coins = 0;
        }
    }

    public void storeCoinsInDB() {
        new DaoSettings().set(DaoSettings.GENERAL_GROUP, DaoSettings.CURRENT_COINS_VAR, "" + coins);
    }
}
