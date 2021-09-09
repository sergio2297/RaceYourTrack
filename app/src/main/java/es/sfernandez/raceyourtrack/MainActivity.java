package es.sfernandez.raceyourtrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import db.dao.DaoSettings;
import es.sfernandez.raceyourtrack.bluetoothConnection.BluetoothActivity;
import es.sfernandez.raceyourtrack.carController.CarControllerActivity;
import es.sfernandez.raceyourtrack.garage.GarageActivity;
import es.sfernandez.raceyourtrack.play.PlayActivity;
import model.Game;
import model.challenges.Challenge;
import model.settings.Settings;
import utils.viewComponents.SwitchButtonC;

public class MainActivity extends AppCompatActivity {

    //---- View Elements ----
    private SwitchButtonC btnGarage, btnPlay;
    private Button btnConnectBluetooth, btnFreeRide;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeData();

        setContentView(R.layout.activity_main);

        // Loading view elements
        btnConnectBluetooth = findViewById(R.id.btn_connect_bluetooth);
        btnConnectBluetooth.setOnClickListener( click -> btnConnectBluetoothOnClick());

        btnFreeRide = findViewById(R.id.btn_free_ride);
        btnFreeRide.setOnClickListener( click -> btnFreeRideOnClick());

        btnGarage = new SwitchButtonC(findViewById(R.id.btn_garage));
        btnGarage.setOnActivateListener( () -> btnGarageOnClick());

        btnPlay = new SwitchButtonC(findViewById(R.id.btn_play));
        btnPlay.setOnActivateListener( () -> btnPlayOnClick());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Game.getInstance().isCarConnected()) {
            btnConnectBluetooth.setBackgroundResource(R.drawable.connect_bluetooth_button);
        } else {
            btnConnectBluetooth.setBackgroundResource(R.drawable.disconnect_bluetooth_button);
        }

        String errorMsg = getIntent().getStringExtra("ERROR_MSG");
        if (errorMsg != null) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }

        btnPlay.deselect();
        btnGarage.deselect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Game.getInstance().destroyCommunicationThread();
    }

    private void initializeData() {
        DaoSettings daoSettings = new DaoSettings();
        if(!daoSettings.isDataInitialized()) {
            Log.println(Log.INFO, Settings.class.getCanonicalName(), "First time? It's necessary to initialize data.");
            Settings.initializeSettingsDB();
            Challenge.initializeChallengesDB();
            Log.println(Log.INFO, Settings.class.getCanonicalName(), "Data has been correctly initialized in database.");

            daoSettings.setDataAsInitialized();
        }
    }

    //---- Methods ----
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnFreeRide.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_free_ride_button));
            }
        });
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

    private void btnPlayOnClick() {
//        List<Challenge> challenges = Challenge.loadAllChallenges(this);
//        for(Challenge challenge : challenges) {
//            Log.i("-----", challenge.toString());
//        }

        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
        getApplicationContext().startActivity(intent);
    }

}
