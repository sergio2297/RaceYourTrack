package es.sfernandez.raceyourtrack.garage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.sfernandez.raceyourtrack.R;
import model.challenges.Challenge;
import utils.ObjectProperty;

public class ListCosmeticsFragment extends Fragment {

    //---- Constants and Definitions ----

    //---- Attributes ----
    private ObjectProperty<Challenge> selectedChallenge;
    private List<Challenge> listChallenges;

    //---- View Elements ----
    private RecyclerView recyclerView;

    //---- Constructor ----
    public ListCosmeticsFragment(){}

    public ListCosmeticsFragment(final List<Challenge> listChallenges) {
        this.selectedChallenge = new ObjectProperty<>(null);
        this.listChallenges = listChallenges;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        settings = Settings.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_challenges, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewElements();
//        addListenersToViewElements();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(recyclerView != null) {
            recyclerView.setAdapter(new ListCosmeticsRecyclerViewAdapter(listChallenges, selectedChallenge));
        }
    }

    private void initializeViewElements() {
        View recyclerViewElement = this.getView().findViewById(R.id.recyclerview_list_challenges);
        if(recyclerViewElement instanceof RecyclerView) {
            this.recyclerView = (RecyclerView) recyclerViewElement;
            this.recyclerView.setAdapter(new ListCosmeticsRecyclerViewAdapter(listChallenges, selectedChallenge));
//            this.recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }
    }

    //---- Methods -----
    public void addOnSelectChallengeListener(final ObjectProperty.ValueChangeListener listener) {
        selectedChallenge.addListener(listener);
    }

    public Challenge getSelectedChallenge() {
        return selectedChallenge.getProperty();
    }
}
