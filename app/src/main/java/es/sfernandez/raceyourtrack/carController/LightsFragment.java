package es.sfernandez.raceyourtrack.carController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import es.sfernandez.raceyourtrack.R;
import model.Car;
import model.Game;
import model.carController.CarController;
import model.carController.LightsSystemController;
import utils.viewComponents.SwitchButtonC;

public class LightsFragment extends Fragment {

    //---- Attributes ----
    private CarController carController;
    private LightsSystemController lightsSystemController;

    //---- View Elements ----
    private SwitchButtonC btnDippedLights, btnMainLights;
    private SwitchButtonC btnLeftBlinking, btnEmergency, btnRightBlinking;

    //---- Constructor ----
    public LightsFragment() {
        lightsSystemController = new LightsSystemController();
    }

    //---- Fragment Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_controller_lights, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewElements();
        addListenersToViewElements();
    }

    //---- Methods ----
    private void initializeViewElements() {
        btnDippedLights = new SwitchButtonC((Button) getView().findViewById(R.id.btn_dipped_lights));
        btnMainLights = new SwitchButtonC((Button) getView().findViewById(R.id.btn_main_lights));
        btnLeftBlinking = new SwitchButtonC((Button) getView().findViewById(R.id.btn_left_blinking));
        btnEmergency = new SwitchButtonC((Button) getView().findViewById(R.id.btn_emergency));
        btnRightBlinking = new SwitchButtonC((Button) getView().findViewById(R.id.btn_right_blinking));

        Car selectedCar = Game.getInstance().getSelectedCar();
        btnMainLights.setVisible(selectedCar.hasMainBeamLights());
        btnLeftBlinking.setVisible(selectedCar.hasBlinkingLights());
        btnEmergency.setVisible(selectedCar.hasBlinkingLights());
        btnRightBlinking.setVisible(selectedCar.hasBlinkingLights());
    }

    private void addListenersToViewElements() {
        if(btnDippedLights.isVisible()) {
            btnDippedLights.setOnActivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOnDippedBeamLights()));
            btnDippedLights.setOnDeactivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOffDippedBeamLights()));
        }

        if(btnMainLights.isVisible()) {
            btnMainLights.setOnActivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOnMainBeamLights()));
            btnMainLights.setOnDeactivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOffMainBeamLights()));
        }

        if(btnLeftBlinking.isVisible()) {
            btnLeftBlinking.setOnActivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOnLeftBlinking()));
            btnLeftBlinking.setOnDeactivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOffLeftBlinking()));
        }

        if(btnEmergency.isVisible()) {
            btnEmergency.setOnActivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOnEmergencyLights()));
            btnEmergency.setOnDeactivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOffEmergencyLights()));
        }

        if(btnRightBlinking.isVisible()) {
            btnRightBlinking.setOnActivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOnRightBlinking()));
            btnRightBlinking.setOnDeactivateListener(() -> carController.addLightsAction(lightsSystemController.buildActionTurnOffRightBlinking()));
        }
    }

    public void setCarController(final CarController carController) {
        this.carController = carController;
    }
}
