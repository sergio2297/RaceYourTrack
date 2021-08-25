package model.soundPlayer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import es.sfernandez.raceyourtrack.R;

public class SoundPlayer {

    //---- Constants and Definitions ----
    private int LAP_CHECK_SOUND, COIN_CHECK_SOUND, MOTOR_ON_SOUND;

    //---- Attributes ----
    private SoundPool soundPool;

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

        loadSounds(context);
    }

    private void loadSounds(Context context) {
        LAP_CHECK_SOUND = soundPool.load(context, R.raw.lap_check, 1);
        COIN_CHECK_SOUND = soundPool.load(context, R.raw.coin_check, 1);
        MOTOR_ON_SOUND = soundPool.load(context, R.raw.motor_on, 1);
    }

    //---- Methods ----
    private void playSingleSound(final int sound) {
        soundPool.play(sound, 1.0f, 1.0f, 1, 0, 1.0f);
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
}
