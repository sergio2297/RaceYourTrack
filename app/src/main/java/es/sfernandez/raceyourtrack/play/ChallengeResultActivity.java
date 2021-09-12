package es.sfernandez.raceyourtrack.play;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import model.Game;
import model.challenges.Challenge;
import model.lapCounter.LapCounter;
import model.raceway.Raceway;
import utils.Utils;

public class ChallengeResultActivity extends AppCompatActivity {

    //---- Constants and Definitions ----

    //---- Attributes ----
    private boolean resultShowed = false;
    private Challenge challenge = Game.getInstance().getSelectedChallenge();
    private LapCounter lapCounter = Game.getInstance().getLapCounter();

    //---- View Elements ----

    private Button btnAccept;

    //---- Constructor ----
    public ChallengeResultActivity() {
        challenge.setPlayerTime(lapCounter.getTotalTime());
        challenge.storeDataInDataBase();
    }

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_result);

        loadViewElements();
    }

    private void loadViewElements() {

        Utils.constructRacewayOnGrid(this, challenge.getRaceway(), (GridLayout) findViewById(R.id.grid_layout_raceway_template), calculateDimensions(challenge.getRaceway()), false);

        ((TextView) findViewById(R.id.txt_challenge_bronze_time)).setText(challenge.getBronzeTime().toString());
        ((TextView) findViewById(R.id.txt_challenge_silver_time)).setText(challenge.getSilverTime().toString());
        ((TextView) findViewById(R.id.txt_challenge_gold_time)).setText(challenge.getGoldTime().toString());

        ((TextView) findViewById(R.id.txt_player_times)).setText("Tu tiempo es: \n" + lapCounter.getLapTimesString());

        if (challenge.getDrawablePlayerMedal() != null) {
            ((TextView) findViewById(R.id.txt_challenge_player_time)).setText("Has conseguido la medalla ");
            ((ImageView) findViewById(R.id.img_player_time_medal)).setImageDrawable(getDrawable(challenge.getDrawablePlayerMedal()));
        } else {
            ((TextView) findViewById(R.id.txt_challenge_player_time)).setText("No has conseguido medalla");
        }

        ((TextView) findViewById(R.id.txt_coins_earned_by_player)).setText("Has ganado: " + challenge.getPlayerCoinsEarned());

        this.btnAccept = findViewById(R.id.btn_accept_challenge_results);
        this.btnAccept.setOnClickListener(e -> {
            if (resultShowed) {
                finish();
            } else {
                showViewElements();
                btnAccept.setText("Aceptar");
                resultShowed = true;
            }
        });
    }

    private void showViewElements() {
        List<View> listComponents = new ArrayList<>();
        listComponents.add(findViewById(R.id.grid_layout_raceway_template));
        listComponents.add(findViewById(R.id.lyt_challenge_request_times));
        listComponents.add(findViewById(R.id.txt_player_times));
        listComponents.add(findViewById(R.id.lyt_player_medal));
        listComponents.add(findViewById(R.id.lyt_coins_earned));
        listComponents.add(findViewById(R.id.btn_accept_challenge_results));

        Iterator<View> it = listComponents.iterator();
        while(it.hasNext()) {
            View view = it.next();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Transition transition = new Fade();
                    transition.setDuration(2000);
                    transition.addTarget(view.getId());

                    TransitionManager.beginDelayedTransition(findViewById(R.id.lyt_challenge_results), transition);
                    view.setVisibility(View.VISIBLE);
                    Game.getInstance().getSoundPlayer().playLapCheckSound();
                }
            };

            runOnUiThread(runnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, PlayActivity.class);
        getApplicationContext().startActivity(intent);
    }

    /**
     * All cells must be square, so it will calculate the max square possible for the current device
     * attending to the number of cells and the dimensions of the other components
     * @return
     */
    private int calculateDimensions(final Raceway raceway) {
        int numOfRowsOnScreen = raceway.getNumOfCellsPerRow();
        int numOfColumnsOnScreen = raceway.getNumOfCellsPerColumn();

        int screenWidth = RaceYourTrackApplication.getScreenWidthPx();
        int screenHeight = RaceYourTrackApplication.getScreenHeightPx();

        return Math.min(screenWidth / numOfColumnsOnScreen, screenHeight / numOfRowsOnScreen);
    }

}
