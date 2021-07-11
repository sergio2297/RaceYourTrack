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

    /*protected static final String SQL_CREATE_LIST_TABLE =
            "CREATE TABLE " + bd.ShoppingListContract.ListTable.TABLE_NAME + " (" +
            bd.ShoppingListContract.ListTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            bd.ShoppingListContract.ListTable.COLUMN_IS_SHOPPING_LIST + " INTEGER NOT NULL," +
            bd.ShoppingListContract.ListTable.COLUMN_IS_STOCK_SHOPPING_LIST + " INTEGER NOT NULL," +
            bd.ShoppingListContract.ListTable.COLUMN_IS_STOCK + " INTEGER NOT NULL," +
            bd.ShoppingListContract.ListTable.COLUMN_NAME + " TEXT NOT NULL," +
            bd.ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + bd.ShoppingListContract.ListTable.COLUMN_ASSOCIATED_STOCK + ") REFERENCES " +
                    bd.ShoppingListContract.ListTable.TABLE_NAME + " (" + bd.ShoppingListContract.ListTable._ID +")" +
            ");";

    protected static final String SQL_CREATE_LIST_PRODUCT_TABLE =
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
    /*protected static final String SQL_DELETE_LIST_TABLE =
            "DROP TABLE IF EXISTS " + bd.ShoppingListContract.ListTable.TABLE_NAME + ";";
    protected static final String SQL_DELETE_LIST_PRODUCT_TABLE =
            "DROP TABLE IF EXISTS " + bd.ShoppingListContract.ListProductTable.TABLE_NAME + ";";*/

    //---- Constructor ----
    public RaceYourTrackDbHelper(Context context, String dataBaseName) {
        super(context, dataBaseName, null, DATA_BASE_VERSION);
    }

    //---- Methods ----
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SETTINGS_TABLE);
        /*db.execSQL(SQL_CREATE_LIST_TABLE);
        db.execSQL(SQL_CREATE_LIST_PRODUCT_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_SETTINGS_TABLE);
        /*db.execSQL(SQL_DELETE_LIST_TABLE);
        db.execSQL(SQL_DELETE_LIST_PRODUCT_TABLE);*/
        onCreate(db);
    }

}
