package model.lapCounter;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.StringTokenizer;

public class TimestampRaceYourTrack implements Comparable<TimestampRaceYourTrack> {

    //---- Constants and Definitions ----
    public static TimestampRaceYourTrack MAX_VALID_TIMESTAMP = new TimestampRaceYourTrack(9,0,0,0);

    //---- Attributes ----
    @SerializedName("hours")
    private final int hours;

    @SerializedName("mins")
    private final int minutes;

    @SerializedName("secs")
    private final int seconds;

    @SerializedName("millis")
    private final int milliseconds;

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

    @Override
    public int compareTo(TimestampRaceYourTrack time) {
        int hoursDiff = this.hours - time.hours;
        int minutesDiff = this.minutes - time.minutes;
        int secondsDiff = this.seconds - time.seconds;
        int millisDiff = this.milliseconds - time.milliseconds;

        return hoursDiff != 0 ? hoursDiff : (
                minutesDiff != 0 ? minutesDiff : (
                        secondsDiff != 0 ? secondsDiff : millisDiff
                        )
                );
    }

    public static TimestampRaceYourTrack fromString(final String string) {
        StringTokenizer st = new StringTokenizer(new StringBuilder(string).reverse().toString(), ":");
        int[] timeUnits = new int[4];
        int i = 0;
        while(i < timeUnits.length && st.hasMoreTokens()) {
            timeUnits[timeUnits.length - i - 1] = Integer.parseInt(new StringBuilder(st.nextToken()).reverse().toString());
            ++i;
        }

        return new TimestampRaceYourTrack(timeUnits[0], timeUnits[1], timeUnits[2], timeUnits[3]);
    }
}
