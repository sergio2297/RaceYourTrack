package model.carController;

public class TransmissionSystemController {

    //---- Constants and Definitions ----
    private final int MIN_NUM_OF_GEARS = 3;
    public final int MAX_NUM_OF_GEARS = 9;
    private final int ASCII_CODE_a = 97;

    /*
     * Actions that cause some movement or concrete behaviour on the car are UPPER_CASE
     * Actions that make some change on car's configuration are lower_case
     *
     * Also it's common that opposite actions have the same character with different case type.
     * (UPPER_CASE -> Positive)
     */
    private final String TRANSMISSION_SYSTEM_ACTION_HEADER = "T_";
    private final char TRANSMISSION_SYSTEM_ACTION_THROTTLE = 'T';
    private final char TRANSMISSION_SYSTEM_ACTION_STOP_THROTTLE = 'S';
    private final char TRANSMISSION_SYSTEM_ACTION_BRAKE = 'B';
    private final char TRANSMISSION_SYSTEM_ACTION_SHIFT_UP = 'U';
    private final char TRANSMISSION_SYSTEM_ACTION_SHIFT_DOWN = 'D';
    // To shift_to action, only is necessary to pass the gear number
    private final char TRANSMISSION_SYSTEM_ACTION_NEUTRAL = 'N';
    private final char TRANSMISSION_SYSTEM_ACTION_REVERSE = 'R';
    private final char TRANSMISSION_SYSTEM_ACTION_H_SHIFT = 'h';
    private final char TRANSMISSION_SYSTEM_ACTION_SEQUENTIAL_SHIFT = 's';
    // To config the number of gears it will be necessary to use ASCII code from characters a = 3 gears; g = 9 gears;

    //---- Methods ----
    private String buildAction(final char action) {
        return TRANSMISSION_SYSTEM_ACTION_HEADER + action;
    }

    public String buildActionConfigHShift() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_H_SHIFT);
    }

    public String buildActionConfigSequentialShift() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_SEQUENTIAL_SHIFT);
    }

    public String buildActionConfigNumOfGears(int numOfGear) {
        if(numOfGear < MIN_NUM_OF_GEARS) {
            numOfGear = MIN_NUM_OF_GEARS;
        } else if (numOfGear > MAX_NUM_OF_GEARS) {
            numOfGear = MAX_NUM_OF_GEARS;
        }

        return buildAction((char) (ASCII_CODE_a + numOfGear - MIN_NUM_OF_GEARS));
    }

    public String buildActionThrottle() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_THROTTLE);
    }

    public String buildActionStopThrottle() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_STOP_THROTTLE);
    }

    public String buildActionBrake() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_BRAKE);
    }

    public String buildActionShiftUp() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_SHIFT_UP);
    }

    public String buildActionShiftDown() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_SHIFT_DOWN);
    }

    public String buildActionShiftTo(final int gear) {
        return buildAction(Character.forDigit(gear, 10));
    }

    public String buildActionNeutral() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_NEUTRAL);
    }

    public String buildActionReverse() {
        return buildAction(TRANSMISSION_SYSTEM_ACTION_REVERSE);
    }
}
