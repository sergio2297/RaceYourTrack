package es.sfernandez.raceyourtrack.carController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import es.sfernandez.raceyourtrack.MainActivity;
import es.sfernandez.raceyourtrack.R;
import model.Game;
import model.carController.CarController;

/**
 * The CarControllerActivity is like a bad boss. It simply create an instance of a CarController
 * object (from model) and reference it with the different fragments necessary to control the car.
 *
 * All the work is done by the CarController and the specified fragments.
 */
public class CarControllerActivity extends AppCompatActivity {

    //---- Attributes ----
    private CarController carController;

    //---- View Elements ----
    private CarInfoFragment carInfoFragment;
    private LightsFragment lightsFragment;
    private PedalsFragment pedalsFragment;
    private SteeringFragment steeringFragment;
    private ChallengeInfoFragment challengeInfoFragment;
    private TransmissionFragment transmissionFragment;

    //---- Constructor ----
    public CarControllerActivity() {
        carController = new CarController(Game.getInstance().getSelectedCar());
    }

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_controller);

        // Enables regular immersive mode.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Loading view elements
        carInfoFragment = (CarInfoFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_info_fragment);

        lightsFragment = (LightsFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_lights_fragment);
        lightsFragment.setCarController(carController);

        pedalsFragment = (PedalsFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_pedals_fragment);
        pedalsFragment.setCarController(carController);

        steeringFragment = (SteeringFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_steering_fragment);
        steeringFragment.setCarController(carController);

        challengeInfoFragment = (ChallengeInfoFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_challenge_info_fragment);
        challengeInfoFragment.getView().setVisibility(Game.getInstance().isChallengeMode() ? View.VISIBLE : View.GONE);

        transmissionFragment = (TransmissionFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_transmission_fragment);
        transmissionFragment.setCarController(carController);
        transmissionFragment.setCarInfoFragment(carInfoFragment);
    }

    @Override
    protected void onDestroy() {
        carController.terminate();

        /*
         * This will return to the Main Activity without destroy and create it again. That's to
         * avoid disconnect from the RC car.
         */
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() { finish(); }

    //---- Methods ----

}
