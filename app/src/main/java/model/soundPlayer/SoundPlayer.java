package model.soundPlayer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.Random;

import es.sfernandez.raceyourtrack.R;
import model.settings.Settings;

public class SoundPlayer {

    //---- Constants and Definitions ----
    private int LAP_CHECK_SOUND, COIN_CHECK_SOUND, MOTOR_ON_SOUND;
    private int F1_PASSING_AWAY_FAST, SPORT_CAR_PASSING_AWAY_FAST;
    private int ELECTRIC_DRILL_SHORT, ELECTRIC_DRILL_LONG, DRILL;
    private int HAMMERING, HAMMERING_FAST;
    private int AND_ITS_LIGHTS_OUT;

    //---- Attributes ----
    private SoundPool soundPool;
    private boolean soundEnabled;

    //---- Construction ----
    public SoundPlayer(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();

        checkSoundEnableSetting();

        loadSounds(context);
    }

    private void loadSounds(Context context) {
        LAP_CHECK_SOUND = soundPool.load(context, R.raw.lap_check, 1);
        COIN_CHECK_SOUND = soundPool.load(context, R.raw.coin_check, 1);
        MOTOR_ON_SOUND = soundPool.load(context, R.raw.motor_on, 1);

        F1_PASSING_AWAY_FAST = soundPool.load(context, R.raw.f1_passing_away_fast, 1);
        SPORT_CAR_PASSING_AWAY_FAST = soundPool.load(context, R.raw.sport_car_passing_away_fast, 1);

        ELECTRIC_DRILL_SHORT = soundPool.load(context, R.raw.electric_drill_short, 1);
        ELECTRIC_DRILL_LONG = soundPool.load(context, R.raw.electric_drill_long, 1);
        DRILL = soundPool.load(context, R.raw.drill, 1);

        HAMMERING = soundPool.load(context, R.raw.hammering, 1);
        HAMMERING_FAST = soundPool.load(context, R.raw.hammering_fast, 1);

        AND_ITS_LIGHTS_OUT = soundPool.load(context, R.raw.start_race_back_count, 1);
    }

    //---- Methods ----
    public void checkSoundEnableSetting() {
        soundEnabled = Settings.getInstance().isSoundsOn();
    }

    private void playSingleSound(final int sound) {
        if(soundEnabled) {
            soundPool.play(sound, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public void playBackCount() {
        playSingleSound(AND_ITS_LIGHTS_OUT);
    }

    public void playLapCheckSound() {
        playSingleSound(LAP_CHECK_SOUND);
    }

    public void playCoinCheckSound() {
        playSingleSound(COIN_CHECK_SOUND);
    }

    public void playMotorOnSound() {
        playSingleSound(MOTOR_ON_SOUND);
    }

    public void playCarPassingAwaySound() {
        int[] soundList = new int[] {F1_PASSING_AWAY_FAST, SPORT_CAR_PASSING_AWAY_FAST};
        playSingleSound(soundList[new Random().nextInt(soundList.length)]);
    }

    public void playBuildSomethingSound() {
        int[] soundList = new int[] {HAMMERING, HAMMERING_FAST, ELECTRIC_DRILL_SHORT, ELECTRIC_DRILL_LONG, DRILL};
        playSingleSound(soundList[new Random().nextInt(soundList.length)]);
    }
}
