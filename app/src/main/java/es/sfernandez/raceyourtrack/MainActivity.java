package es.sfernandez.raceyourtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.sfernandez.raceyourtrack.garage.GarageActivity;

public class MainActivity extends AppCompatActivity {

    //---- View Elements ----
    private Button btnConnectBluetooth, btnFreeRide;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DataBaseUtils.deleteAllDataStored(getApplicationContext()); // TODO: Delete later

        // Loading view elements
        btnConnectBluetooth = findViewById(R.id.btnConnectBluetooth);

    }

    //---- Methods ----
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnConnectBluetooth:
                Toast.makeText(getApplicationContext(), "Boton pulsado", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnFreeRide:
                intent = new Intent(getApplicationContext(), CarControllerActivity.class);
                intent.putExtra("ID", 10);
                getApplicationContext().startActivity(intent);
                break;

            case R.id.btnGarage:
                intent = new Intent(getApplicationContext(), GarageActivity.class);
                intent.putExtra("ID", 10);
                getApplicationContext().startActivity(intent);
                break;
        }
    }
}
