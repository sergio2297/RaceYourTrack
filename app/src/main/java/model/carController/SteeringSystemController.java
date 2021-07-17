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
    // between [0,9] and a letter (for turn left) between [A,J] to use its ASCII code.

    //---- Methods ----
    private String buildAction(final char action) {
        return STEERING_SYSTEM_ACTION_HEADER + action;
    }

    public String buildActionSteeringLeft() {
        return buildAction(STEERING_SYSTEM_ACTION_LEFT);
    }

    public String buildActionSteeringLeft(final int degrees) {
        if(degrees <= 0) {
            return buildActionCenterSteering();
        } else if(degrees >= 90) {
            return buildActionSteeringLeft();
        } else {
            return buildAction(Character.toUpperCase(Character.forDigit(Math.round(degrees / 10f) + 10, 20)));
        }
    }

    public String buildActionCenterSteering() {
        return buildAction(STEERING_SYSTEM_ACTION_MIDDLE);
    }

    public String buildActionSteeringRight(final int degrees) {
        if(degrees <= 0) {
            return buildActionCenterSteering();
        } else if(degrees >= 90) {
            return buildActionSteeringRight();
        } else {
            return buildAction(Character.forDigit(Math.round(degrees / 10f), 10));
        }
    }

    public String buildActionSteeringRight() {
        return buildAction(STEERING_SYSTEM_ACTION_RIGHT);
    }
}
