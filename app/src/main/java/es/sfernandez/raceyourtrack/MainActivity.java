package es.sfernandez.raceyourtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import db.DataBaseUtils;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHelper;
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

        DataBaseUtils.deleteAllDataStored(getApplicationContext()); // TODO: Delete later

        // Loading view elements
        btnConnectBluetooth = findViewById(R.id.btn_connect_bluetooth);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Game.getInstance().destroyCommunicationThread();
    }

    //---- Methods ----
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_connect_bluetooth:
                intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                break;

            case R.id.btn_free_ride:
                if(Game.getInstance().isCarConnected()) {
                    intent = new Intent(getApplicationContext(), CarControllerActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                    intent.putExtra("targetActivity", CarControllerActivity.class);
                }
                break;

            case R.id.btn_garage:
                intent = new Intent(getApplicationContext(), GarageActivity.class);
                break;

            default:
                throw new AppUnCatchableException(new AppError(AppErrorHelper.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
        getApplicationContext().startActivity(intent);
    }
}
