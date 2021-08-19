package es.sfernandez.raceyourtrack.garage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import es.sfernandez.raceyourtrack.MainActivity;
import es.sfernandez.raceyourtrack.R;
import utils.viewComponents.SwitchButtonC;

public class GarageActivity extends AppCompatActivity {

    //---- Attributes ----

    //---- View Elements ----
    private Fragment fragmentWelcome, fragmentCars, fragmentSettings;
    private Fragment fragmentActive;

    private SwitchButtonC btnCars, btnSettings;

    //---- Activity Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        initializeViewElements();
        addListenersToViewElements();

        fragmentWelcome = new DialogFragment();
        fragmentCars = new DialogFragment();
        fragmentSettings = new SettingsFragment();
    }

    @Override
    protected void onDestroy() {
        /*
         * This will return to the Main Activity without destroy and create it again. That's to
         * avoid disconnect from the RC car.
         */
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        super.onDestroy();
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
    private void initializeViewElements() {
        btnCars = new SwitchButtonC(findViewById(R.id.btn_cars));
        btnSettings = new SwitchButtonC(findViewById(R.id.btn_settings));
    }

    private void addListenersToViewElements() {
        btnCars.setOnActivateListener( () -> {
            btnSettings.deselect();
            moveToFragment(fragmentCars);
        });
        btnCars.setOnDeactivateListener( () -> {
            moveToFragment(fragmentWelcome);
        });

        btnSettings.setOnActivateListener( () -> {
            btnCars.deselect();
            moveToFragment(fragmentSettings);
        });
        btnSettings.setOnDeactivateListener( () -> {
            moveToFragment(fragmentWelcome);
        });
    }

    private void moveToFragment(final Fragment fragmentToMove) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.garage_fragment_container, fragmentToMove);
        fragmentActive = fragmentToMove;
        transaction.addToBackStack(null).commit();
    }
}
