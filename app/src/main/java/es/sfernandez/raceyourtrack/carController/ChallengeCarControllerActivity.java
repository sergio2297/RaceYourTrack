package es.sfernandez.raceyourtrack.carController;

import android.os.Bundle;

import es.sfernandez.raceyourtrack.play.ChallengeResultActivity;
import model.Game;
import model.lapCounter.LapCounter;

public class ChallengeCarControllerActivity extends CarControllerActivity {

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(2000); //Para esperar que suene el del boton correr
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LapCounter lapCounter = Game.getInstance().getLapCounter();
        lapCounter.initialize(Game.getInstance().getSelectedChallenge().getNumberOfLaps(),
                Game.getInstance().getSelectedChallenge().hasSecret());
        lapCounter.addListener(code -> {
            if(code == LapCounter.CODE_END) {
                Game.getInstance().getSoundPlayer().playLapCheckSound();
                finish();
            }
        });
        Game.getInstance().setChallengeMode(true);
        Game.getInstance().getSoundPlayer().playBackCount();
        try {
            Thread.sleep(2900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lapCounter.start();

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        Game.getInstance().setChallengeMode(false);
        this.activityClassToReturn = ChallengeResultActivity.class;
        super.onDestroy();
    }
}
