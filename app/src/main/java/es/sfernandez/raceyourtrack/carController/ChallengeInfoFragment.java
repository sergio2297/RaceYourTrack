package es.sfernandez.raceyourtrack.carController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import es.sfernandez.raceyourtrack.R;
import model.Game;
import model.lapCounter.LapCounter;

public class ChallengeInfoFragment extends Fragment {

    //---- Constants and Definitions ----

    //---- Attributes ----
    private LapCounter lapCounter;

    //---- View Elements ----
    private TextView txtLapCounter, txtCoinChecked;

    //---- Constructor ----
    public ChallengeInfoFragment() {
        lapCounter = Game.getInstance().getLapCounter();
        lapCounter.addListener( (code) -> {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    repaintComponents();
                }
            });
        });
    }

    //---- Fragment Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_controller_challenge_info, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewElements();
    }

    //---- Methods ----
    private void initializeViewElements() {
        this.txtLapCounter = getView().findViewById(R.id.txt_lap_counter);
        this.txtCoinChecked = getView().findViewById(R.id.txt_coin_checked);
        repaintComponents();
    }

    private void repaintComponents() {
        this.txtLapCounter.setText("Vuelta: " + lapCounter.getCurrentLap() + "/" + lapCounter.getNumOfLaps());
        this.txtCoinChecked.setVisibility(lapCounter.thereIsSpecialCheckpoint() ? View.VISIBLE : View.GONE);
        if(this.txtCoinChecked.getVisibility() == View.VISIBLE) {
            this.txtCoinChecked.setBackgroundResource(lapCounter.isSpecialCheckpointFounded() ? R.drawable.png_coin : R.drawable.png_coin_disabled);
        }
    }
}
