package es.sfernandez.raceyourtrack.garage;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import es.sfernandez.raceyourtrack.R;

public class GarageActivity extends AppCompatActivity {

    //---- Attributes ----

    //---- View Elements ----
    private Fragment fragmentSettings, fragmentCars;
    private Fragment fragmentActive;

    //---- Activity Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        fragmentSettings = new SettingsFragment();
        fragmentCars = new DialogFragment();
    }

    //---- Methods ----
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(view.getId()) {
            case R.id.btnCars:
                transaction.replace(R.id.garageFragmentContainer, fragmentCars);
                fragmentActive = fragmentCars;
                break;

            case R.id.btnSettings:
                transaction.replace(R.id.garageFragmentContainer, fragmentSettings);
                fragmentActive = fragmentSettings;
                break;

        }
        transaction.addToBackStack(null); // FIXME: ???
        transaction.commit();
    }

}
