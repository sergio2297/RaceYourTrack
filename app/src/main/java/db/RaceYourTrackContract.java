package db;

import android.provider.BaseColumns;

public final class RaceYourTrackContract {

    private RaceYourTrackContract() {}

    //---- SETTINGS TABLE ----
    public static abstract class SettingsTable implements BaseColumns {
        public static final String TABLE_NAME = "Settings";
        public static final String COLUMN_GROUP = "grupo"; // It can't be 'group' because of 'group' is a reserved word
        public static final String COLUMN_VARIABLE = "variable";
        public static final String COLUMN_VALUE = "value";
    }

    //---- CARS TABLE ----
    public static abstract class CarsTable implements BaseColumns {
        public static final String TABLE_NAME = "Cars";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TRANSMISSION = "transmission";
        public static final String COLUMN_NUM_OF_GEARS = "numOfGears";
        public static final String COLUMN_HAS_MAIN_BEAM_LIGHTS = "hasMainBeamLights";
        public static final String COLUMN_HAS_BLINKING_LIGHTS = "hasBlinkingLights";
    }

    //---- CHALLENGE TABLE ----
    public static abstract class ChallengesTable implements BaseColumns {
        public static final String TABLE_NAME = "Challenges";
        public static final String COLUMN_CHALLENGE = "challenge";
        public static final String COLUMN_UNLOCKED = "unlocked";
        public static final String COLUMN_PLAYER_TIME = "playerTime";
    }

}
