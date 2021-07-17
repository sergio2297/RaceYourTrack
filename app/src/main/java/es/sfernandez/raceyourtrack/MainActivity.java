package es.sfernandez.raceyourtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import db.DataBaseUtils;
import es.sfernandez.raceyourtrack.carController.CarControllerActivity;
import es.sfernandez.raceyourtrack.garage.GarageActivity;

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

    //---- Methods ----
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_connect_bluetooth:
                Toast.makeText(getApplicationContext(), "Boton pulsado", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_free_ride:
                intent = new Intent(getApplicationContext(), CarControllerActivity.class);
                intent.putExtra("ID", 10);
                getApplicationContext().startActivity(intent);
                break;

            case R.id.btn_garage:
                intent = new Intent(getApplicationContext(), GarageActivity.class);
                intent.putExtra("ID", 10);
                getApplicationContext().startActivity(intent);
                break;
        }
    }
}
