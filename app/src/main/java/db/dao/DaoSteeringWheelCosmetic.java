package db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import db.IdentifiedObjectClass;
import db.RaceYourTrackContract;

public class DaoSteeringWheelCosmetic extends AbstractDao {

    //---- Constructor ----
    public DaoSteeringWheelCosmetic() {super();}

    //---- Methods ----
    public boolean existsARowForTheCosmetic(final int cosmeticId) {
        boolean result = false;

        String[] columns = {RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_COSMETIC};
        String where = RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_COSMETIC + " = ?";
        String[] whereArgs = {Integer.toString(cosmeticId)};

        Cursor cursor = dbConnection.query(RaceYourTrackContract.SteeringWheelCosmeticTable.TABLE_NAME, columns, where, whereArgs, null, null, null);
        try {
            result = cursor.getCount() == 1;
        } finally {
            cursor.close();
        }

        return result;
    }

    public boolean getIsCosmeticUnlocked(final int cosmeticId) {
        boolean result = true;

        String[] columns = {RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_UNLOCKED};
        String where = RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_COSMETIC + " = ?";
        String[] whereArgs = {Integer.toString(cosmeticId)};

        Cursor cursor = dbConnection.query(RaceYourTrackContract.SteeringWheelCosmeticTable.TABLE_NAME, columns, where, whereArgs, null, null, null);
        try {
            while(cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_UNLOCKED));
                result = value.equals("1");
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public void setCosmeticIsUnlocked(final int cosmeticId, final boolean unlocked) {
        ContentValues values = new ContentValues();
        values.put(RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_UNLOCKED, unlocked);

        if(!existsARowForTheCosmetic(cosmeticId)) {   // It's necessary to do an insert
            values.put(RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_COSMETIC, cosmeticId);

            dbConnection.insert(RaceYourTrackContract.SteeringWheelCosmeticTable.TABLE_NAME, null, values);
        } else {            // It's necessary to do an update
            String where = RaceYourTrackContract.SteeringWheelCosmeticTable.COLUMN_COSMETIC + " = ?";
            String[] whereArgs = {Integer.toString(cosmeticId)};

            dbConnection.update(RaceYourTrackContract.SteeringWheelCosmeticTable.TABLE_NAME, values, where, whereArgs);
        }
    }

    @Override
    public IdentifiedObjectClass findById(int id) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    protected int doInsert(IdentifiedObjectClass elem) {
        return 0;
    }

    @Override
    protected long doUpdate(IdentifiedObjectClass elem) {
        return 0;
    }

    @Override
    protected long doRemove(IdentifiedObjectClass elem) {
        return 0;
    }


}
