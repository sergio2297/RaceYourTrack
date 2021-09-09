package db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import db.IdentifiedObjectClass;
import db.RaceYourTrackContract;
import model.lapCounter.TimestampRaceYourTrack;

public class DaoChallenges extends AbstractDao {

    //---- Constructor ----
    public DaoChallenges() {super();}

    //---- Methods ----
    public boolean existsARowForTheChallenge(final int challengeId) {
        boolean result = false;

        String[] columns = {RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE};
        String where = RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE + " = ?";
        String[] whereArgs = {Integer.toString(challengeId)};

        Cursor cursor = dbConnection.query(RaceYourTrackContract.ChallengesTable.TABLE_NAME, columns, where, whereArgs, null, null, null);
        try {
            result = cursor.getCount() == 1;
        } finally {
            cursor.close();
        }

        return result;
    }

    public boolean getIsChallengeUnlocked(final int challengeId) {
        boolean result = true;

        String[] columns = {RaceYourTrackContract.ChallengesTable.COLUMN_UNLOCKED};
        String where = RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE + " = ?";
        String[] whereArgs = {Integer.toString(challengeId)};

        Cursor cursor = dbConnection.query(RaceYourTrackContract.ChallengesTable.TABLE_NAME, columns, where, whereArgs, null, null, null);
        try {
            while(cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(RaceYourTrackContract.ChallengesTable.COLUMN_UNLOCKED));
                result = value.equals("1");
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public void setChallengeIsUnlocked(final int challengeId, final boolean unlocked) {
        ContentValues values = new ContentValues();
        values.put(RaceYourTrackContract.ChallengesTable.COLUMN_UNLOCKED, unlocked);

        if(!existsARowForTheChallenge(challengeId)) {   // It's necessary to do an insert
            values.put(RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE, challengeId);
            values.put(RaceYourTrackContract.ChallengesTable.COLUMN_PLAYER_TIME, new TimestampRaceYourTrack(0,0,0,0).toString());

            dbConnection.insert(RaceYourTrackContract.ChallengesTable.TABLE_NAME, null, values);
        } else {            // It's necessary to do an update
            String where = RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE + " = ?";
            String[] whereArgs = {Integer.toString(challengeId)};

            dbConnection.update(RaceYourTrackContract.ChallengesTable.TABLE_NAME, values, where, whereArgs);
        }
    }

    public TimestampRaceYourTrack getPlayerTime(final int challengeId) {
        TimestampRaceYourTrack result = null;

        String[] columns = {RaceYourTrackContract.ChallengesTable.COLUMN_PLAYER_TIME};
        String where = RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE + " = ?";
        String[] whereArgs = {Integer.toString(challengeId)};

        Cursor cursor = dbConnection.query(RaceYourTrackContract.ChallengesTable.TABLE_NAME, columns, where, whereArgs, null, null, null);
        try {
            while(cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex(RaceYourTrackContract.ChallengesTable.COLUMN_PLAYER_TIME));
                result = TimestampRaceYourTrack.fromString(value);
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public void setPlayerTime(final int challengeId, final TimestampRaceYourTrack time) {
        ContentValues values = new ContentValues();
        values.put(RaceYourTrackContract.ChallengesTable.COLUMN_PLAYER_TIME, time.toString());

        if(!existsARowForTheChallenge(challengeId)) {   // It's necessary to do an insert
            values.put(RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE, challengeId);
            values.put(RaceYourTrackContract.ChallengesTable.COLUMN_UNLOCKED, false);

            dbConnection.insert(RaceYourTrackContract.ChallengesTable.TABLE_NAME, null, values);
        } else {            // It's necessary to do an update
            String where = RaceYourTrackContract.ChallengesTable.COLUMN_CHALLENGE + " = ?";
            String[] whereArgs = {Integer.toString(challengeId)};

            dbConnection.update(RaceYourTrackContract.ChallengesTable.TABLE_NAME, values, where, whereArgs);
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
