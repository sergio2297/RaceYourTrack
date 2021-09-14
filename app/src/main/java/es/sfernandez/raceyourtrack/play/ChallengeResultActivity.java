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

        ((TextView) findViewById(R.id.txt_current_player_coins)).setText("" + Game.getInstance().getCoins());
        ((TextView) findViewById(R.id.txt_challenge_name)).setText(challenge.getRaceway().getName());

        Utils.constructRacewayOnGrid(this, challenge.getRaceway(), (GridLayout) findViewById(R.id.grid_layout_raceway_template), calculateDimensions(challenge.getRaceway()) - 40, false);

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

        if(challenge.hasSecret()) {
            if(lapCounter.isSpecialCheckpointFounded()) {
                ((TextView) findViewById(R.id.txt_special_check_found)).setText("¡Has recogido la moneda secreta!");
                ((ImageView) findViewById(R.id.img_special_check)).setImageDrawable(getDrawable(R.drawable.png_coin));
            } else {
                ((TextView) findViewById(R.id.txt_special_check_found)).setText("No has recogido la moneda secreta");
                ((ImageView) findViewById(R.id.img_special_check)).setImageDrawable(getDrawable(R.drawable.png_coin_disabled));
            }
        } else {
            findViewById(R.id.img_special_check).setVisibility(View.GONE);
        }

        ((TextView) findViewById(R.id.txt_coins_earned_by_player)).setText("Has ganado: " + challenge.getPlayerCoinsEarned()
            + ((lapCounter.isSpecialCheckpointFounded() && challenge.hasSecret()) ? " + " + Challenge.SPECIAL_CHECK_COINS_AWARD : ""));

        this.btnAccept = findViewById(R.id.btn_accept_challenge_results);
        this.btnAccept.setOnClickListener(e -> {
            if (resultShowed) {
                Game.getInstance().setCoins(Game.getInstance().getCoins() + challenge.getPlayerCoinsEarned() +
                        ((lapCounter.isSpecialCheckpointFounded() && challenge.hasSecret()) ? Challenge.SPECIAL_CHECK_COINS_AWARD : 0));
                ((TextView) findViewById(R.id.txt_current_player_coins)).setText("" + Game.getInstance().getCoins());
//                Game.getInstance().getSoundPlayer().playCoinCheckSound();
                Game.getInstance().storeCoinsInDB();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
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
        listComponents.add(findViewById(R.id.txt_challenge_name));
        listComponents.add(findViewById(R.id.grid_layout_raceway_template));
        listComponents.add(findViewById(R.id.lyt_challenge_request_times));
        listComponents.add(findViewById(R.id.txt_player_times));
        listComponents.add(findViewById(R.id.lyt_player_medal));
//        listComponents.add(findViewById(R.id.lyt_coins_earned));
        listComponents.add(findViewById(R.id.lyt_special_check));
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
