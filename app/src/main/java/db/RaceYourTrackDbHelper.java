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

    /*protected static final String SQL_CREATE_LIST_PRODUCT_TABLE =
            "CREATE TABLE " + bd.ShoppingListContract.ListProductTable.TABLE_NAME + " (" +
            bd.ShoppingListContract.ListProductTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            bd.ShoppingListContract.ListProductTable.COLUMN_LIST_ID + " INTEGER NOT NULL," +
            bd.ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + bd.ShoppingListContract.ListProductTable.COLUMN_LIST_ID + ") REFERENCES " +
                    bd.ShoppingListContract.ListTable.TABLE_NAME + " (" + bd.ShoppingListContract.ListTable._ID +")," +
            "FOREIGN KEY (" + bd.ShoppingListContract.ListProductTable.COLUMN_PRODUCT_ID + ") REFERENCES " +
                    bd.ShoppingListContract.ProductTable.TABLE_NAME + " (" + bd.ShoppingListContract.ProductTable._ID +")" +
            ");";*/

    protected static final String SQL_DELETE_SETTINGS_TABLE =
            "DROP TABLE IF EXISTS " + RaceYourTrackContract.SettingsTable.TABLE_NAME + ";";
    protected static final String SQL_DELETE_CARS_TABLE =
            "DROP TABLE IF EXISTS " + RaceYourTrackContract.CarsTable.TABLE_NAME + ";";
    /*protected static final String SQL_DELETE_LIST_PRODUCT_TABLE =
            "DROP TABLE IF EXISTS " + bd.ShoppingListContract.ListProductTable.TABLE_NAME + ";";*/

    //---- Constructor ----
    public RaceYourTrackDbHelper(Context context, String dataBaseName) {
        super(context, dataBaseName, null, DATA_BASE_VERSION);
    }

    //---- Methods ----
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SETTINGS_TABLE);
        db.execSQL(SQL_CREATE_CARS_TABLE);
        /*db.execSQL(SQL_CREATE_LIST_PRODUCT_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_SETTINGS_TABLE);
        db.execSQL(SQL_DELETE_CARS_TABLE);
        /*db.execSQL(SQL_DELETE_LIST_PRODUCT_TABLE);*/
        onCreate(db);
    }

}
