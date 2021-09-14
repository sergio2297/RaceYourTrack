package es.sfernandez.raceyourtrack.play;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import es.sfernandez.raceyourtrack.MainActivity;
import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.play.racewayBuilding.RacewayGuideBuildingActivity;
import model.Game;
import model.challenges.Challenge;
import utils.viewComponents.SwitchButtonC;

public class PlayActivity extends AppCompatActivity {

    //---- Attributes ----
    private final List<Challenge> listCircuitsChallenges = new ArrayList<>();
    private final List<Challenge> listTracksChallenges = new ArrayList<>();
    private boolean challengeSelected = false;

    //---- View Elements ----
    private ListChallengesFragment fragmentCircuits, fragmentTracks, fragmentSpecial;
    private Fragment fragmentActive;

    private SwitchButtonC btnCircuits, btnTracks, btnSpecial;

    //---- Activity Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        loadChallenges();

        initializeViewElements();
        addListenersToViewElements();

        fragmentCircuits = new ListChallengesFragment(listCircuitsChallenges);
        fragmentCircuits.addOnSelectChallengeListener((oldValue, newValue) -> {
                if(newValue != null)
                    onChallengeSelected();
        });
        fragmentTracks = new ListChallengesFragment(listTracksChallenges);
        fragmentTracks.addOnSelectChallengeListener((oldValue, newValue) -> {
            if(newValue != null)
                onChallengeSelected();
        });
//        fragmentSpecial = new ListChallengesFragment();

        btnCircuits.click();
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Challenge> listChallenges = new ArrayList<>();
        listChallenges.addAll(listCircuitsChallenges);
        listChallenges.addAll(listTracksChallenges);

        for(Challenge challenge : listChallenges) {
            if(!challenge.isUnlocked() && challenge.canBeUnlocked(listChallenges)) {
                challenge.unlock();
                challenge.storeDataInDataBase();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!challengeSelected) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //---- Methods ----
    private void loadChallenges() {
        List<Challenge> listChallenges = Challenge.loadAllChallenges(this);
        for(Challenge challenge : listChallenges) {
            switch (challenge.getType()) {
                case CIRCUIT:
                    listCircuitsChallenges.add(challenge);
                    break;

                case TRACK:
                    listTracksChallenges.add(challenge);
                    break;

                default:
                    break;
            }
        }
    }

    private void initializeViewElements() {
        btnCircuits = new SwitchButtonC(findViewById(R.id.btn_circuits));
        btnTracks = new SwitchButtonC(findViewById(R.id.btn_tracks));
//        btnSpecial = new SwitchButtonC(findViewById(R.id.btn_specials));
        findViewById(R.id.btn_specials).setVisibility(View.GONE);
    }

    private void addListenersToViewElements() {
        btnCircuits.setOnActivateListener( () -> {
            btnTracks.deselect();
            moveToFragment(fragmentCircuits);
        });

        btnTracks.setOnActivateListener( () -> {
            btnCircuits.deselect();
            moveToFragment(fragmentTracks);
        });
    }

    private void moveToFragment(final Fragment fragmentToMove) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.play_fragment_container, fragmentToMove);
        fragmentActive = fragmentToMove;
        transaction.addToBackStack(null).commit();
    }

    private void onChallengeSelected() {
        Challenge selectedChallenge = ((ListChallengesFragment)fragmentActive).getSelectedChallenge();
        Game.getInstance().setSelectedChallenge(selectedChallenge);

        challengeSelected = true;

        Intent intent = new Intent(getApplicationContext(), RacewayGuideBuildingActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
