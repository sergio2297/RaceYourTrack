package model.carController;

public class TransmissionSystemController {

    //---- Constants and Definitions ----
    /*
     * Actions that cause some movement or concrete behaviour on the car are UPPER_CASE
     * Actions that make some change on car's configuration are lower_case
     *
     * Also it's common that oppsite actions have the same character with different case type.
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
    private final char TRANSMISSION_SYSTEM_ACTION_SECUENCIAL_SHIFT = 's';
    // To config the number of gears it will be neccesary to use ASCII code from characters

    //---- Attributes ----

    //---- Construction ----
    public TransmissionSystemController() {

    }

    //---- Methods ----


}
