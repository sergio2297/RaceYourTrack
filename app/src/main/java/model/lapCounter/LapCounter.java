package model.lapCounter;

import android.util.Log;

import java.util.Arrays;
import java.util.Iterator;

import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;

public class LapCounter {

    //---- Constants and Definitions ----
    private final char LAP_CHECKPOINT_KEY = 'C';
    private final char SPECIAL_CHECKPOINT_KEY = 'K';

    //---- Attributes ----
    private boolean initialized = false;

    private TimestampRaceYourTrack[] lapTimes;
    private long lastTimestamp;
    private int numOfLaps, currentLap;
    private boolean isWorking;
    private boolean thereIsSpecialCheckpoint, specialCheckpointFounded;

    //---- Construction ----
    public LapCounter() {}

    //---- Methods ----
    public void initialize(final int numOfLaps, final boolean thereIsSpecialCheckpoint) {
        this.lastTimestamp = -1;
        this.isWorking = false;

        this.numOfLaps = numOfLaps;
        this.currentLap = 0;
        this.lapTimes = new TimestampRaceYourTrack[numOfLaps];

        this.thereIsSpecialCheckpoint = thereIsSpecialCheckpoint;
        this.specialCheckpointFounded = false;

        this.initialized = true;
    }

    public void start() {
        if(!initialized) {
            throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.MUST_NOT_HAPPEN_LAP_COUNTER_NOT_INITIALIZED, "", RaceYourTrackApplication.getContext()));
        }
        lastTimestamp = System.currentTimeMillis();
        isWorking = true;
    }

    public void checkPassed(final char checkedKey) {
        switch(checkedKey) {
            case LAP_CHECKPOINT_KEY:
                long currentTimestamp = System.currentTimeMillis();
                lapTimes[currentLap] = TimestampRaceYourTrack.calculateTimestamp(lastTimestamp, currentTimestamp);
                lastTimestamp = currentTimestamp;
                ++currentLap;
                if(currentLap == numOfLaps) {
                    end();
                }
                break;

            case SPECIAL_CHECKPOINT_KEY:
                specialCheckpointFounded = thereIsSpecialCheckpoint ? true : false;
                break;

            default:
                break;
        }
    }

    public void end() {
        isWorking = false;
        initialized = false;
        printResult();
    }

    public boolean isStarted() {
        return isWorking;
    }

    public boolean isSpecialCheckpointFounded() {
        return specialCheckpointFounded;
    }

    public Iterator<TimestampRaceYourTrack> getLapTimes() {
        return Arrays.stream(lapTimes).iterator();
    }

    private void printResult() {
        for(int i = 0; i < numOfLaps; ++i) {
            Log.i("LapCounter", "Lap " + i + ": " + lapTimes[i].toString());
        }
        Log.i("LapCounter", "SpecialCheckPointFounded: " + specialCheckpointFounded);
    }


}
