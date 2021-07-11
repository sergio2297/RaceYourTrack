package model.carController;

public class SteeringSystemController {

    //---- Constants and Definitions ----
    /*
     * Actions that cause some movement or concrete behaviour on the car are UPPER_CASE
     * Actions that make some change on car's configuration are lower_case
     *
     * Also it's common that oppsite actions have the same character with different case type.
     * (UPPER_CASE -> Positive)
     */
    private final String STEERING_SYSTEM_ACTION_HEADER = "S_";
    private final char STEERING_SYSTEM_ACTION_RIGHT = 'R';
    private final char STEERING_SYSTEM_ACTION_MIDDLE = 'M';
    private final char STEERING_SYSTEM_ACTION_LEFT = 'L';
    // To turn the steering a concrete amount of degrees it will be necessary to pass a number (for turn right)
    // beteween [0,9] and a letter (for turn left) between [A,J] to use its ASCII code.

    //---- Attributes ----

    //---- Construction ----

    //---- Methods ----
    private String constructCommand(final char action) {
        return STEERING_SYSTEM_ACTION_HEADER + action;
    }

    public String steeringLeft() {
        return constructCommand(STEERING_SYSTEM_ACTION_LEFT);
    }

    public String steeringLeft(final int degrees) {
        //TODO:
        return "";
    }

    public String centerSteering() {
        return constructCommand(STEERING_SYSTEM_ACTION_MIDDLE);
    }

    public String steeringRight(final int degrees) {
        //TODO:
        return "";
    }

    public String steeringRight() {
        return constructCommand(STEERING_SYSTEM_ACTION_RIGHT);
    }
}
