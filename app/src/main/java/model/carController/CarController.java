package model.carController;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class CarController {

    //---- Constants and Definitions ----
    private final char SYSTEM_END_COMMAND = '$';
    private final char SYSTEM_END_ACTION = ';';
    private final char SYSTEM_NO_ACTION_CODE =  ' ';

    //---- Attributes ----
    private Context context;
    private LightSystemController lightSystemController = new LightSystemController();
    private SteeringSystemController steeringSystemController = new SteeringSystemController();
    private TransmissionSystemController transmissionSystemController = new TransmissionSystemController();

    //---- Construction ----
    public CarController(final Context context) {
        this.context = context;
    }

    //---- Methods ----
    public void configRcCar() {
        //TODO:
    }

    private String constructCommand(final String action) {
        String command = "";
        command += action + SYSTEM_END_ACTION;
        return command += SYSTEM_END_COMMAND;
    }

    private void sendCommand(final String command) {
        //TODO: send command using bluetooth
        Toast.makeText(context, command, Toast.LENGTH_SHORT).show();
    }

    //---- Steering System ----
    public void steeringLeft() {
        sendCommand(constructCommand(steeringSystemController.steeringLeft()));
    }

    public void steeringLeft(final int degrees) {
        sendCommand(constructCommand(steeringSystemController.steeringLeft(degrees)));
    }

    public void centerSteering() {
        sendCommand(constructCommand(steeringSystemController.centerSteering()));
    }

    public void steeringRight(final int degrees) {
        sendCommand(constructCommand(steeringSystemController.steeringRight(degrees)));
    }

    public void steeringRight() {
        sendCommand(constructCommand(steeringSystemController.steeringRight()));
    }
}
