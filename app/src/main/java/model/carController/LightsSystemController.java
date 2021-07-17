package model.carController;

public class LightsSystemController {

    //---- Constants and Definitions ----
    /*
     * Actions that cause some movement or concrete behaviour on the car are UPPER_CASE
     * Actions that make some change on car's configuration are lower_case
     *
     * Also it's common that opposite actions have the same character with different case type.
     * (UPPER_CASE -> Positive)
     */
    private final String LIGHTS_SYSTEM_ACTION_HEADER = "L_";
    private final char LIGHTS_SYSTEM_ACTION_TURN_ON_DIPPED_BEAM_LIGHTS = 'D';
    private final char LIGHTS_SYSTEM_ACTION_TURN_OFF_DIPPED_BEAM_LIGHTS = 'd';
    private final char LIGHTS_SYSTEM_ACTION_TURN_ON_MAIN_BEAM_LIGHTS = 'M';
    private final char LIGHTS_SYSTEM_ACTION_TURN_OFF_MAIN_BEAM_LIGHTS = 'm';
    private final char LIGHTS_SYSTEM_ACTION_TURN_ON_BRAKE_LIGHTS = 'B';
    private final char LIGHTS_SYSTEM_ACTION_TURN_OFF_BRAKE_LIGHTS = 'b';
    private final char LIGHTS_SYSTEM_ACTION_TURN_ON_REVERSE_LIGHTS = 'P';
    private final char LIGHTS_SYSTEM_ACTION_TURN_OFF_REVERSE_LIGHTS = 'p';
    private final char LIGHTS_SYSTEM_ACTION_TURN_ON_LEFT_BLINKING = 'L';
    private final char LIGHTS_SYSTEM_ACTION_TURN_OFF_LEFT_BLINKING = 'l';
    private final char LIGHTS_SYSTEM_ACTION_TURN_ON_RIGHT_BLINKING = 'R';
    private final char LIGHTS_SYSTEM_ACTION_TURN_OFF_RIGHT_BLINKING = 'r';
    private final char LIGHTS_SYSTEM_ACTION_TURN_ON_EMERGENCY_LIGHTS = 'E';
    private final char LIGHTS_SYSTEM_ACTION_TURN_OFF_EMERGENCY_LIGHTS = 'e';

    //---- Methods ----
    private String buildAction(final char action) {
        return LIGHTS_SYSTEM_ACTION_HEADER + action;
    }

    public String buildActionTurnOnDippedBeamLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_ON_DIPPED_BEAM_LIGHTS);
    }

    public String buildActionTurnOffDippedBeamLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_OFF_DIPPED_BEAM_LIGHTS);
    }

    public String buildActionTurnOnMainBeamLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_ON_MAIN_BEAM_LIGHTS);
    }

    public String buildActionTurnOffMainBeamLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_OFF_MAIN_BEAM_LIGHTS);
    }

    public String buildActionTurnOnBrakeLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_ON_BRAKE_LIGHTS);
    }

    public String buildActionTurnOffBrakeLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_OFF_BRAKE_LIGHTS);
    }

    public String buildActionTurnOnReverseLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_ON_REVERSE_LIGHTS);
    }

    public String buildActionTurnOffReverseLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_OFF_REVERSE_LIGHTS);
    }

    public String buildActionTurnOnLeftBlinking() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_ON_LEFT_BLINKING);
    }

    public String buildActionTurnOffLeftBlinking() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_OFF_LEFT_BLINKING);
    }

    public String buildActionTurnOnEmergencyLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_ON_EMERGENCY_LIGHTS);
    }

    public String buildActionTurnOffEmergencyLights() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_OFF_EMERGENCY_LIGHTS);
    }

    public String buildActionTurnOnRightBlinking() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_ON_RIGHT_BLINKING);
    }

    public String buildActionTurnOffRightBlinking() {
        return buildAction(LIGHTS_SYSTEM_ACTION_TURN_OFF_RIGHT_BLINKING);
    }
}
