package model.carController;

import java.util.LinkedList;
import java.util.Queue;

import model.Car;
import model.Game;
import model.settings.configurable.TransmissionConfig;

/**
 * A CarController performance the work necessary for send the commands to the Bluetooth module with
 * the specified period in the constants.
 *
 * The lifecycle is:
 *      1º A button/component is activated by the user. This component has a listener added previously by his
 *      respective fragment in the creation.
 *      2º Normally, this listener has referenced an action offered by some SystemController.
 *      3º The SystemController will build the command which will be sent to the rc car so that it
 *      cans be interpreted by the Arduino.
 *      4º After build the command, the SystemController add the command to the respective commandsQueue of the
 *      CarController.
 *      5º Lastly, the CarController every period will construct a complete command (one action for every system)
 *      with the first action stored in every queue and send it by Bluetooth.
 */
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
        if(!command.equals("" + SYSTEM_END_COMMAND)) { // iff the command isn't empty
            Game.getInstance().sendMessageToRcCar(command);
        }
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
