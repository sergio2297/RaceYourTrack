package model.lapCounter;

import androidx.annotation.NonNull;

public class TimestampRaceYourTrack {

    //---- Attributes ----
    private final int hours, minutes, seconds, milliseconds;

    //---- Construction ----
    public TimestampRaceYourTrack(final int hours, final int minutes, final int seconds, final int milliseconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }

    //---- Methods ----
    public static TimestampRaceYourTrack calculateTimestamp(final long startPointMs, final long endPointMs) {
        int hours = 0, minutes = 0, seconds = 0, milliseconds = 0;
        long timeMilliseconds = endPointMs - startPointMs;

        seconds = (int) (timeMilliseconds / 1000);
        minutes = seconds / 60;
        hours = minutes / 60;

        milliseconds = (int) (timeMilliseconds % 1000);
        seconds = seconds % 60;
        minutes = minutes % 60;

        return new TimestampRaceYourTrack(hours, minutes, seconds, milliseconds);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    @NonNull
    @Override
    public String toString() {
        String result = "";
        if(hours > 0) {
            result += (hours >= 10 ? hours : "0" + hours) + ":";
        }

        result += (minutes >= 10 ? minutes : "0" + minutes) + ":";
        result += (seconds >= 10 ? seconds : "0" + seconds) + ":";
        result += (milliseconds >= 100 ? milliseconds : "0" + (milliseconds >= 10 ? milliseconds : "0" + milliseconds));

        return result;
    }
}
