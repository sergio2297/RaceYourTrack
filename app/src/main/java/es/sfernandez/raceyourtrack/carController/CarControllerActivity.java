package es.sfernandez.raceyourtrack.carController;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.sfernandez.raceyourtrack.R;
import model.Game;
import model.carController.CarController;

public class CarControllerActivity extends AppCompatActivity {

    //---- Attributes ----
    private CarController carController;

    //---- View Elements ----
    private LightsFragment lightsFragment;
    private PedalsFragment pedalsFragment;
    private SteeringFragment steeringFragment;
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

        // Loading view elements
        lightsFragment = (LightsFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_lights_fragment);
        lightsFragment.setCarController(carController);

        pedalsFragment = (PedalsFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_pedals_fragment);
        pedalsFragment.setCarController(carController);

        steeringFragment = (SteeringFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_steering_fragment);
        steeringFragment.setCarController(carController);

        transmissionFragment = (TransmissionFragment) getSupportFragmentManager().findFragmentById(R.id.car_controller_transmission_fragment);
        transmissionFragment.setCarController(carController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carController.terminate();
    }

    //---- Methods ----

}
