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

    /*//---- LIST TABLE ----
    public static abstract class ListTable implements BaseColumns {
        public static final String TABLE_NAME = "Listado";
        public static final String COLUMN_IS_SHOPPING_LIST = "esListaCompra";
        public static final String COLUMN_IS_STOCK_SHOPPING_LIST = "esListaCompraInventario";
        public static final String COLUMN_IS_STOCK = "esInventario";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_ASSOCIATED_STOCK = "listadoAsociado";
    }

    //---- LIST_PRODUCT TABLE
    public static abstract class ListProductTable implements BaseColumns {
        public static final String TABLE_NAME = "ListadoProducto";
        public static final String COLUMN_LIST_ID = "listado";
        public static final String COLUMN_PRODUCT_ID = "producto";
    }*/
}
