package es.sfernandez.raceyourtrack.play;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.sfernandez.raceyourtrack.R;
import model.challenges.Challenge;
import model.lapCounter.TimestampRaceYourTrack;
import utils.ObjectProperty;

public class ListChallengesRecyclerViewAdapter extends RecyclerView.Adapter<ListChallengesRecyclerViewAdapter.ViewHolder> {

    //---- Constants and Definitions ----
    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //---- Attributes ----
        protected final View view;
        protected final Context context;

        protected ObjectProperty<Challenge> fragmentSelectedChallenge;
        protected Challenge myChallenge;

        //---- Constructor ----
        public ViewHolder(View view, ObjectProperty<Challenge> fragmentSelectedChallenge) {
            super(view);
            this.view = view;
            this.context = view.getContext();
            this.fragmentSelectedChallenge = fragmentSelectedChallenge;
        }

        //---- Methods ----
        public void printChallenge(final Challenge challenge) {
            myChallenge = challenge;

            if(myChallenge.isUnlocked()) {
                this.view.setClickable(true);
                this.view.setOnClickListener(this);

                view.findViewById(R.id.challenge_view_holder_container).setBackgroundResource(R.drawable.challenge_unlocked_view_holder_container);

                ((TextView) view.findViewById(R.id.txt_challenge_raceway_name)).setText(challenge.getRaceway().getName());
                ((TextView) view.findViewById(R.id.txt_challenge_laps)).setText(
                        String.format(view.getResources().getString(R.string.number_of_laps), challenge.getNumberOfLaps()));
                ((TextView) view.findViewById(R.id.txt_challenge_difficulty)).setText("" + challenge.getDifficulty());

                ((TextView) view.findViewById(R.id.txt_challenge_bronze_time)).setText(challenge.getBronzeTime().toString());
                ((TextView) view.findViewById(R.id.txt_challenge_silver_time)).setText(challenge.getSilverTime().toString());
                ((TextView) view.findViewById(R.id.txt_challenge_gold_time)).setText(challenge.getGoldTime().toString());

                ((ImageView) view.findViewById(R.id.img_player_time_medal)).setImageDrawable(challenge.getDrawablePlayerMedal() != null ? view.getResources().getDrawable(challenge.getDrawablePlayerMedal()) : null);
                ((TextView) view.findViewById(R.id.txt_challenge_player_time)).setText(challenge.getPlayerTime().compareTo(TimestampRaceYourTrack.MAX_VALID_TIMESTAMP) < 0 ? challenge.getPlayerTime().toString() : "");
            } else {
                this.view.setClickable(false);

                view.findViewById(R.id.challenge_view_holder_container).setBackgroundResource(R.drawable.challenge_locked_view_holder_container);

                ((TextView) view.findViewById(R.id.txt_challenge_raceway_name)).setText(challenge.getRaceway().getName());
                ((TextView) view.findViewById(R.id.txt_challenge_laps)).setText(
                        String.format(view.getResources().getString(R.string.number_of_laps), challenge.getNumberOfLaps()));
                ((TextView) view.findViewById(R.id.txt_challenge_difficulty)).setText("" + challenge.getDifficulty());

                ((TextView) view.findViewById(R.id.txt_challenge_bronze_time)).setText("???");
                ((TextView) view.findViewById(R.id.txt_challenge_silver_time)).setText("???");
                ((TextView) view.findViewById(R.id.txt_challenge_gold_time)).setText("???");

                ((ImageView) view.findViewById(R.id.img_player_time_medal)).setImageDrawable(null);
                ((TextView) view.findViewById(R.id.txt_challenge_player_time)).setText("");
            }
        }

        @Override
        public void onClick(View v) {
            fragmentSelectedChallenge.setProperty(myChallenge);
        }
    }

    //---- Attributes ----
    private final List<Challenge> listChallenges;
    protected final ObjectProperty<Challenge> fragmentSelectedChallenge;

    //---- Constructor ----
    public ListChallengesRecyclerViewAdapter(final List<Challenge> listChallenges, ObjectProperty<Challenge> selectedChallenge) {
        this.listChallenges = listChallenges;
        this.fragmentSelectedChallenge = selectedChallenge;
    }

    //---- Methods ----
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_holder_challenges, parent, false), fragmentSelectedChallenge);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.printChallenge(listChallenges.get(position));
    }

    @Override
    public int getItemCount() {
        return listChallenges.size();
    }

}
