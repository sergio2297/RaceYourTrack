package model.carController;

import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

import model.Car;
import model.settings.configurable.TransmissionConfig;

public class CarController extends Thread {

    //---- Constants and Definitions ----
    private final int PERIOD_MS = 50;
    private final char SYSTEM_END_ACTION = ';';
    private final char SYSTEM_END_COMMAND = '$';
    private final char SYSTEM_NO_ACTION_CODE =  ' ';

    //---- Attributes ----
    private boolean endExecution = false;

    private Queue<String> queueTransmissionActions = new LinkedList<String>();
    private Queue<String> queueSteeringActions = new LinkedList<String>();
    private Queue<String> queueLightActions = new LinkedList<String>();

    //---- Construction ----
    public CarController(final Car car) {
        configRcCar(car);
        start();
    }

    //---- Methods ----
    @Override
    public void run() {
        long timestamp = System.currentTimeMillis();
        while(!endExecution) {
            if(System.currentTimeMillis() - PERIOD_MS >= timestamp) {
                timestamp += PERIOD_MS;

                String command = constructCommand();
                sendCommand(command);
            }
        }
    }

    public void terminate() {
        queueTransmissionActions.clear();
        queueSteeringActions.clear();
        queueLightActions.clear();

        endExecution = true;
    }

    private void configRcCar(final Car car) {
        TransmissionSystemController transmissionSystemController = new TransmissionSystemController();
        this.addTransmissionAction(
                car.getTransmissionConfig().equals(TransmissionConfig.H_SHIFT) ?
                    new TransmissionSystemController().buildActionConfigHShift() :
                    new TransmissionSystemController().buildActionConfigSequentialShift()
                );
        this.addTransmissionAction(transmissionSystemController.buildActionConfigNumOfGears(car.getNumOfGears()));
    }

    private String constructCommand() {
        String command = "";
        if(!queueTransmissionActions.isEmpty()) {
            command += queueTransmissionActions.poll() + SYSTEM_END_ACTION;
        }
        if(!queueSteeringActions.isEmpty()) {
            command += queueSteeringActions.poll() + SYSTEM_END_ACTION;
        }
        if(!queueLightActions.isEmpty()) {
            command += queueLightActions.poll() + SYSTEM_END_ACTION;
        }
        return command + SYSTEM_END_COMMAND;
    }

    private void sendCommand(final String command) {
        //TODO: send command using bluetooth
        Log.i("", command);
    }

    public void addTransmissionAction(final String action) {
        queueTransmissionActions.add(action);
    }

    public void addSteeringAction(final String action) {
        queueSteeringActions.add(action);
    }

    public void addLightsAction(final String action) {
        queueLightActions.add(action);
    }
}
