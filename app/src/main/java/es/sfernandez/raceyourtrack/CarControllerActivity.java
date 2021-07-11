package es.sfernandez.raceyourtrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import model.carController.CarController;

public class CarControllerActivity extends AppCompatActivity {

    //---- Attributes ----
    private CarController carController;

    private Button btnThrottle;
    private Button btnSteeringLeft, btnSteeringRight;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_controller);

        carController = new CarController(getApplicationContext());

        // Loading view elements
        btnThrottle = findViewById(R.id.btnConnectBluetooth);
        btnSteeringLeft = findViewById(R.id.btnSteeringLeft);
        btnSteeringRight = findViewById(R.id.btnSteeringRight);
    }

    //---- Methods ----
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnThrottle:
                Toast.makeText(getApplicationContext(), "Throttle", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnSteeringLeft:
                carController.steeringLeft();
                break;

            case R.id.btnSteeringRight:
                carController.steeringRight();
                break;
        }
    }

}
