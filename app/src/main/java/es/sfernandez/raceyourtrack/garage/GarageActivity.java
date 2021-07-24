package es.sfernandez.raceyourtrack.garage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import es.sfernandez.raceyourtrack.MainActivity;
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
    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(view.getId()) {
            case R.id.btn_cars:
                transaction.replace(R.id.garage_fragment_container, fragmentCars);
                fragmentActive = fragmentCars;
                break;

            case R.id.btn_Settings:
                transaction.replace(R.id.garage_fragment_container, fragmentSettings);
                fragmentActive = fragmentSettings;
                break;

        }
        transaction.addToBackStack(null); // FIXME: ???
        transaction.commit();
    }

}
