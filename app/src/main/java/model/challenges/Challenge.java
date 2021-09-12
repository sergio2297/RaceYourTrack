
package model.challenges;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import db.dao.DaoChallenges;
import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import model.lapCounter.TimestampRaceYourTrack;
import model.raceway.Raceway;
import utils.Utils;

public class Challenge {

    //---- Constants and Definitions ----
    public class Score {
        @SerializedName("requestTime")
        private final TimestampRaceYourTrack requestTime;

        @SerializedName("coins")
        private final int coins;

        public Score(final TimestampRaceYourTrack requestTime, final int coins) {
            this.requestTime = requestTime;
            this.coins = coins;
        }

        public TimestampRaceYourTrack getRequestTime() {
            return requestTime;
        }
        
        public int getCoins() {
            return coins;
        }
        
        public boolean scorePassed(final TimestampRaceYourTrack playerTime) {
            return playerTime.compareTo(requestTime) <= 0;
        }

        @Override
        public String toString() {
            return "Score{" +
                    "requestTime=" + requestTime +
                    ", coins=" + coins +
                    '}';
        }
    }

    //---- Attributes ----
    @SerializedName("id")
    private final int id;

    @SerializedName("racewayFile")
    private final String racewayFileName;

    @SerializedName("scores")
    private final Score[] scores;

    @SerializedName("laps")
    private final int laps;

    @SerializedName("challengesRequired")
    private final int[] challengesRequired;

    private final Raceway raceway;
    private final boolean hasSecret;
    private final int difficulty;

    private boolean unlocked;
    private TimestampRaceYourTrack playerTime;

    //---- Construction ----
    public Challenge(final int id, final String racewayFileName, final Score[] scores, final int laps, final int[] challengesRequired) {
        this.id = id;
        this.racewayFileName = racewayFileName;
        this.raceway = Raceway.loadFromJson(racewayFileName);
        this.raceway.initPiecesCount();
        this.hasSecret = raceway.hasSecret();
        this.difficulty = (raceway.calculateDifficulty() * laps) / 10; //A number between 1 and 5
        this.scores = scores;
        this.laps = laps;
        this.challengesRequired = challengesRequired;

        loadDataFromDataBase();
    }

    private void loadDataFromDataBase() {
        DaoChallenges dao = new DaoChallenges();
        if(!dao.existsARowForTheChallenge(this.id)) {
            this.unlocked = !(this.challengesRequired.length > 0);
            this.playerTime = TimestampRaceYourTrack.MAX_VALID_TIMESTAMP;
        } else {
            this.unlocked = dao.getIsChallengeUnlocked(this.id);
            this.playerTime = dao.getPlayerTime(this.id);
        }
    }

    public void storeDataInDataBase() {
        DaoChallenges dao = new DaoChallenges();
        dao.setChallengeIsUnlocked(this.id, this.unlocked);
        dao.setPlayerTime(this.id, this.playerTime);
    }

    public void unlock() {
        unlocked = true;
    }

    private Challenge findChallenge(final List<Challenge> listChallenges, final int challengeId) {
        Challenge challenge = null;
        int i = 0;
        while(i < listChallenges.size() && challenge == null) {
            if(listChallenges.get(i).id == challengeId) {
                challenge = listChallenges.get(i);
            } else {
                ++i;
            }
        }
        return challenge;
    }

    public boolean canBeUnlocked(final List<Challenge> listChallenges) {
        boolean canBeUnlocked = true;
        int i = 0;
        while(i < challengesRequired.length && canBeUnlocked) {
            canBeUnlocked = findChallenge(listChallenges, challengesRequired[i]).hasBeenComplete();
            ++i;
        }
        return canBeUnlocked;
    }

    //---- Instance Methods ----

    public int getNumberOfLaps() {
        return laps;
    }

    public Raceway getRaceway() {
        return raceway;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public TimestampRaceYourTrack getBronzeTime() {
        return scores[0].getRequestTime();
    }

    public TimestampRaceYourTrack getSilverTime() {
        return scores[1].getRequestTime();
    }

    public TimestampRaceYourTrack getGoldTime() {
        return scores[2].getRequestTime();
    }

    public Integer getDrawablePlayerMedal() {
        Integer drawableId = null;

        if(playerTime.compareTo(scores[2].getRequestTime()) <= 0) {
            drawableId = new Integer(R.drawable.png_gold_coin);
        } else if(playerTime.compareTo(scores[1].getRequestTime()) <= 0) {
            drawableId = new Integer(R.drawable.png_silver_coin);
        } else if(playerTime.compareTo(scores[0].getRequestTime()) <= 0) {
            drawableId = new Integer(R.drawable.png_bronze_coin);
        }

        return drawableId;
    }

    public Integer getPlayerCoinsEarned() {
        if(playerTime.compareTo(scores[2].getRequestTime()) <= 0) { // Gold
            return scores[2].coins;
        } else if(playerTime.compareTo(scores[1].getRequestTime()) <= 0) { // Silver
            return scores[1].coins;
        } else if(playerTime.compareTo(scores[0].getRequestTime()) <= 0) { // Bronze
            return scores[0].coins;
        } else {
            return 0;
        }
    }

    public TimestampRaceYourTrack getPlayerTime() {
        return playerTime;
    }

    public Raceway.Type getType() {
        return raceway.getType();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public boolean hasSecret() {
        return hasSecret;
    }

    public boolean hasBeenComplete() {
        return playerTime != null && playerTime.compareTo(scores[0].getRequestTime()) <= 0;
    }

    public void setPlayerTime(final TimestampRaceYourTrack time) {
        this.playerTime = time;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "id=" + id +
                ", racewayFileName='" + racewayFileName + '\'' +
                ", scores=" + Arrays.toString(scores) +
                ", laps=" + laps +
                ", raceway=" + raceway +
                ", hasSecret=" + hasSecret +
                ", difficulty=" + difficulty +
                ", challengesRequired=" + Arrays.toString(challengesRequired) +
                ", unlocked=" + unlocked +
                ", playerTime=" + playerTime +
                '}';
    }

    //---- Static Methods ----
    public static void initializeChallengesDB() {
        List<Challenge> challenges = loadAllChallenges(RaceYourTrackApplication.getContext());
        for(Challenge challenge : challenges) {
            challenge.storeDataInDataBase();
        }
    }

    public static List<Challenge> loadAllChallenges(Context context) {
        String contentJson = Utils.getJsonStringFromAssets(context, "challenges.json");
        Challenge[] challenges = new Gson().fromJson(contentJson, Challenge[].class);

        List<Challenge> listChallenges = new ArrayList<>();
        for(Challenge challenge : challenges) {
            listChallenges.add(new Challenge(challenge.id, challenge.racewayFileName, challenge.scores, challenge.laps, challenge.challengesRequired));
        }

        return listChallenges;
    }

}
