package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseUtils {

    //---- Constants and Definitions ----
    private static SQLiteDatabase writableBdConnection = null, readableBdConnection = null;

    //---- Constants and Definitions ----
    private static final String DATA_BASE_NAME = "race_your_track_data.db";

    //---- Static Methods ----
    public static SQLiteDatabase getWritableDatabaseConnection(Context context) {
        if(writableBdConnection == null) {
            RaceYourTrackDbHelper dbHelper = new RaceYourTrackDbHelper(context, DATA_BASE_NAME);
            writableBdConnection = dbHelper.getWritableDatabase();
        }
        return writableBdConnection;
    }

    protected static SQLiteDatabase getReadableDatabaseConnection(Context context) {
        if(readableBdConnection == null) {
            RaceYourTrackDbHelper dbHelper = new RaceYourTrackDbHelper(context,DATA_BASE_NAME);
            readableBdConnection = dbHelper.getReadableDatabase();
        }
        return readableBdConnection;
    }

    public static void deleteAllDataStored(Context context) {
        SQLiteDatabase bd = getWritableDatabaseConnection(context);

        bd.execSQL(RaceYourTrackDbHelper.SQL_DELETE_SETTINGS_TABLE);
        bd.execSQL(RaceYourTrackDbHelper.SQL_DELETE_CARS_TABLE);
        bd.execSQL(RaceYourTrackDbHelper.SQL_DELETE_CHALLENGES_TABLE);

        bd.execSQL(RaceYourTrackDbHelper.SQL_CREATE_SETTINGS_TABLE);
        bd.execSQL(RaceYourTrackDbHelper.SQL_CREATE_CARS_TABLE);
        bd.execSQL(RaceYourTrackDbHelper.SQL_CREATE_CHALLENGES_TABLE);
    }

}
