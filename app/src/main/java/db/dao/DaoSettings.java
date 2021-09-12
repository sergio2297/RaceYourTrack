package db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import db.IdentifiedObjectClass;
import db.RaceYourTrackContract;
import model.settings.SettingsRow;

public class DaoSettings extends AbstractDao {

    //---- Constants and Definitions ----
    public static final String GENERAL_GROUP = "GENERAL";
    public static final String DATA_INITIALIZED_VAR = "DATA_INITIALIZED";
    public static final String SELECTED_CAR_VAR = "SELECTED_CAR";
    public static final String CURRENT_COINS_VAR = "CURRENT_COINS";
    public static final String FALSE_VALUE = "FALSE";
    public static final String TRUE_VALUE = "TRUE";

    public static final String CONTROL_GROUP = "CONTROL";
    public static final String CONTROL_CONFIG_TYPE_VAR = "CONFIG_TYPE";
    public static final String PEDALS_VAR = "PEDALS";
    public static final String STEERING_VAR = "STEERING";
    public static final String TRANSMISSION_VAR = "TRANSMISSION";

    public static final String SOUND_GROUP = "SOUND";
    public static final String ENABLE_VAR = "ENABLE";

    //---- Construction ----
    public DaoSettings() {
        super();
    }

    //---- Methods ----
    private void validateGroup(final String group) {
        List<String> listAcceptableGroups = new ArrayList<String>();
        listAcceptableGroups.add(GENERAL_GROUP);
        listAcceptableGroups.add(CONTROL_GROUP);
        listAcceptableGroups.add(SOUND_GROUP);
        if(!listAcceptableGroups.contains(group)) {
            throw new RuntimeException("Group isn't acceptable " + group);
        }
    }

    private void validateVariable(final String group, final String variable) {
        List<String> listAcceptableVariables = new ArrayList<String>();

        if(group.equals(GENERAL_GROUP)) {
            listAcceptableVariables.add(DATA_INITIALIZED_VAR);
            listAcceptableVariables.add(SELECTED_CAR_VAR);
            listAcceptableVariables.add(CURRENT_COINS_VAR);
        } else if(group.equals(CONTROL_GROUP)) {
            listAcceptableVariables.add(CONTROL_CONFIG_TYPE_VAR);
            listAcceptableVariables.add(STEERING_VAR);
            listAcceptableVariables.add(PEDALS_VAR);
            listAcceptableVariables.add(TRANSMISSION_VAR);
        } else if(group.equals(SOUND_GROUP)) {
            listAcceptableVariables.add(ENABLE_VAR);
        }

        if(!listAcceptableVariables.contains(variable)) {
            throw new RuntimeException("Variable isn't acceptable " + variable);
        }
    }

    /**
     * @return false iff the application's settings hasn't been initialized
     */
    public boolean isDataInitialized() {
        return get(GENERAL_GROUP, DATA_INITIALIZED_VAR) != null &&
                get(GENERAL_GROUP, DATA_INITIALIZED_VAR).getValue() == TRUE_VALUE;
    }

    /**
     * Indicates that the application's settings has been initialized
     */
    public void setDataAsInitialized() {
        set(GENERAL_GROUP, DATA_INITIALIZED_VAR, TRUE_VALUE);
    }

    /**
     * @return a instance of {@link SettingsRow} with the value loaded from the db or null if it is not
     * found.
     * @throws {@link RuntimeException} if group or variable isn't acceptable.
     */
    public SettingsRow get(final String group, final String variable) {
        SettingsRow result = null;

        validateGroup(group);
        validateVariable(group, variable);

        // Preparing the query params
        String[] columns = {RaceYourTrackContract.SettingsTable.COLUMN_VALUE};
        String where = RaceYourTrackContract.SettingsTable.COLUMN_GROUP + " = ? " +
                "AND " + RaceYourTrackContract.SettingsTable.COLUMN_VARIABLE + " = ? ";
        String[] whereArgs = {group, variable};

        // Exec the query and store the results in a Cursor
        Cursor cursor = dbConnection.query(RaceYourTrackContract.SettingsTable.TABLE_NAME,
                columns, where, whereArgs, null, null, null);
        try {
            while(cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(RaceYourTrackContract.SettingsTable.COLUMN_VALUE));
                result = new SettingsRow(group, variable, value);
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    /**
     * Set the specified values in db.
     * @throws {@link RuntimeException} if group or variable isn't acceptable.
     */
    public void set(final SettingsRow row) {
        set(row.getGroup(), row.getVariable(), row.getValue());
    }

    /**
     * Set the specified values in db.
     * @throws {@link RuntimeException} if group or variable isn't acceptable.
     */
    public void set(final String group, final String variable, final String value) {
        SettingsRow row = get(group, variable);

        ContentValues values = new ContentValues();
        values.put(RaceYourTrackContract.SettingsTable.COLUMN_VALUE, value);

        if(row == null) {   // It's necessary to do an insert
            values.put(RaceYourTrackContract.SettingsTable.COLUMN_GROUP, group);
            values.put(RaceYourTrackContract.SettingsTable.COLUMN_VARIABLE, variable);

            dbConnection.insert(RaceYourTrackContract.SettingsTable.TABLE_NAME, null, values);
        } else {            // It's necessary to do an update
            String where = RaceYourTrackContract.SettingsTable.COLUMN_GROUP + " = ? " +
                    "AND " + RaceYourTrackContract.SettingsTable.COLUMN_VARIABLE + " = ? ";
            String[] whereArgs = {group, variable};

            dbConnection.update(RaceYourTrackContract.SettingsTable.TABLE_NAME, values, where, whereArgs);
        }
    }

    /**
     * @Deprecated Use method SettingsRow get(String group, String variable) instead.
     */
    @Override @Deprecated
    public IdentifiedObjectClass findById(int id) {
        return null;
    }

    /**
     * @Deprecated Use method SettingsRow get(String group, String variable) instead.
     */
    @Override @Deprecated
    public List<IdentifiedObjectClass> findAll() {
        return null;
    }

    /**
     * @Deprecated Use method SettingsRow set(String group, String variable, String value) instead.
     */
    @Override @Deprecated
    protected int doInsert(IdentifiedObjectClass elem) {
        return 0;
    }

    /**
     * @Deprecated Use method SettingsRow set(String group, String variable, String value) instead.
     */
    @Override @Deprecated
    protected long doUpdate(IdentifiedObjectClass elem) {
        return 0;
    }

    /**
     * @Deprecated Use method SettingsRow set(String group, String variable, String value) instead.
     */
    @Override @Deprecated
    protected long doRemove(IdentifiedObjectClass elem) {
        return 0;
    }

}
