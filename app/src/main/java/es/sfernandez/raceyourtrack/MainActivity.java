package es.sfernandez.raceyourtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;
import es.sfernandez.raceyourtrack.bluetoothConnection.BluetoothActivity;
import es.sfernandez.raceyourtrack.carController.CarControllerActivity;
import es.sfernandez.raceyourtrack.garage.GarageActivity;
import model.Game;

public class MainActivity extends AppCompatActivity {

    //---- View Elements ----
    private Button btnConnectBluetooth, btnFreeRide;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DataBaseUtils.deleteAllDataStored(getApplicationContext()); // TODO: Delete later

        // Loading view elements
        btnConnectBluetooth = findViewById(R.id.btn_connect_bluetooth);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!Game.getInstance().isCarConnected()) {
            btnConnectBluetooth.setText(getResources().getString(R.string.connect_bluetooth));
        } else {
            btnConnectBluetooth.setText(getResources().getString(R.string.disconnect_bluetooth));
        }

        String errorMsg = getIntent().getStringExtra("ERROR_MSG");
        if(errorMsg != null) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // FIXME: Cuando vuelvo del Free Ride se llama a este método porque estoy creando una nueva MainActivity al hacer el intent
        Game.getInstance().destroyCommunicationThread();
    }

    //---- Methods ----
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_connect_bluetooth:
                btnConnectBluetoothOnClick();
                break;

            case R.id.btn_free_ride:
                btnFreeRideOnClick();
                break;

            case R.id.btn_garage:
                btnGarageOnClick();
                break;

            default:
                throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
    }

    private void btnConnectBluetoothOnClick() {
        if(!Game.getInstance().isCarConnected()) {
            Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
            getApplicationContext().startActivity(intent);
        } else {
            Game.getInstance().destroyCommunicationThread();
            this.onResume();
        }
    }

    private void btnFreeRideOnClick() {
        Intent intent;
        if(Game.getInstance().isCarConnected()) {
            intent = new Intent(getApplicationContext(), CarControllerActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), BluetoothActivity.class);
            intent.putExtra("targetActivity", CarControllerActivity.class);
        }
        getApplicationContext().startActivity(intent);
    }

    private void btnGarageOnClick() {
        Intent intent = new Intent(getApplicationContext(), GarageActivity.class);
        getApplicationContext().startActivity(intent);
    }
}
