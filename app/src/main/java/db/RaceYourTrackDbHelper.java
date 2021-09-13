package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RaceYourTrackDbHelper extends SQLiteOpenHelper {

    //---- Constants and Definitions ----
    public static final int DATA_BASE_VERSION = 1;

    protected static final String SQL_CREATE_SETTINGS_TABLE =
            "CREATE TABLE " + RaceYourTrackContract.SettingsTable.TABLE_NAME + " (" +
            RaceYourTrackContract.SettingsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RaceYourTrackContract.SettingsTable.COLUMN_GROUP + " TEXT NOT NULL," +
            RaceYourTrackContract.SettingsTable.COLUMN_VARIABLE + " TEXT NOT NULL," +
            RaceYourTrackContract.SettingsTable.COLUMN_VALUE + " TEXT" +
            ");";

    protected static final String SQL_CREATE_CARS_TABLE =
            "CREATE TABLE " + RaceYourTrackContract.CarsTable.TABLE_NAME + " (" +
            RaceYourTrackContract.CarsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RaceYourTrackContract.CarsTable.COLUMN_NAME + " TEXT NOT NULL," +
            RaceYourTrackContract.CarsTable.COLUMN_TRANSMISSION + " TEXT NOT NULL," +
            RaceYourTrackContract.CarsTable.COLUMN_NUM_OF_GEARS + " INTEGER NOT NULL," +
            RaceYourTrackContract.CarsTable.COLUMN_HAS_MAIN_BEAM_LIGHTS + " BOOLEAN NOT NULL " +
                    "CHECK (" + RaceYourTrackContract.CarsTable.COLUMN_HAS_MAIN_BEAM_LIGHTS + " IN (0, 1)), " +
            RaceYourTrackContract.CarsTable.COLUMN_HAS_BLINKING_LIGHTS + " BOOLEAN NOT NULL " +
                    "CHECK (" + RaceYourTrackContract.CarsTable.COLUMN_HAS_BLINKING_LIGHTS + " IN (0, 1)) " +
            ");";

    protected static final String SQL_CREATE_CHALLENGES_TABLE =
            "CREATE TABLE " + RaceYourTrackContract.ChallengesTable.TABLE_NAME + " (" +
            RaceYourTrackContract.ChallengesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE + " INTEGER NOT NULL," +
            RaceYourTrackContract.ChallengesTable.COLUMN_UNLOCKED + " BOOLEAN NOT NULL," +
            RaceYourTrackContract.ChallengesTable.COLUMN_PLAYER_TIME + " TEXT NOT NULL" +
            ");";

    protected static final String SQL_CREATE_STEERING_WHEEL_COSMETICS_TABLE =
            "CREATE TABLE " + RaceYourTrackContract.SteeringWheelCosmeticTable.TABLE_NAME + " (" +
                    RaceYourTrackContract.SteeringWheelCosmeticTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_COSMETIC + " INTEGER NOT NULL," +
                    RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_UNLOCKED + " BOOLEAN NOT NULL" +
                    ");";

    protected static final String SQL_DELETE_SETTINGS_TABLE =
            "DROP TABLE IF EXISTS " + RaceYourTrackContract.SettingsTable.TABLE_NAME + ";";
    protected static final String SQL_DELETE_CARS_TABLE =
            "DROP TABLE IF EXISTS " + RaceYourTrackContract.CarsTable.TABLE_NAME + ";";
    protected static final String SQL_DELETE_CHALLENGES_TABLE =
            "DROP TABLE IF EXISTS " + RaceYourTrackContract.ChallengesTable.TABLE_NAME + ";";
    protected static final String SQL_DELETE_STEERING_WHEEL_COSMETICS_TABLE =
            "DROP TABLE IF EXISTS " + RaceYourTrackContract.SteeringWheelCosmeticTable.TABLE_NAME + ";";

    //---- Constructor ----
    public RaceYourTrackDbHelper(Context context, String dataBaseName) {
        super(context, dataBaseName, null, DATA_BASE_VERSION);
    }

    //---- Methods ----
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SETTINGS_TABLE);
        db.execSQL(SQL_CREATE_CARS_TABLE);
        db.execSQL(SQL_CREATE_CHALLENGES_TABLE);
        db.execSQL(SQL_CREATE_STEERING_WHEEL_COSMETICS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_SETTINGS_TABLE);
        db.execSQL(SQL_DELETE_CARS_TABLE);
        db.execSQL(SQL_DELETE_CHALLENGES_TABLE);
        db.execSQL(SQL_DELETE_STEERING_WHEEL_COSMETICS_TABLE);
        onCreate(db);
    }

}
