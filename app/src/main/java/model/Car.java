package model;

import db.IdentifiedObjectClass;
import model.settings.configurable.TransmissionConfig;

public class Car extends IdentifiedObjectClass {

    //---- Constants and Definitions ----
    public static final Car SPORT_CAR = new Car(0, "Rapidster", TransmissionConfig.SEQUENTIAL_SHIFT, 5, true, false);
    public static final Car MUSCLE_CAR = new Car(1, "Muscleitor", TransmissionConfig.H_SHIFT, 3,false, true);

    //---- Attributes ----
    private final String name;
    private final TransmissionConfig transmissionConfig;
    private final int numOfGears;
    private final boolean hasMainBeamLights, hasBlinkingLights;

    //---- Constructor -----
    public Car(final int id, final String name, final TransmissionConfig transmissionConfig, final int numOfGears,
               final boolean hasMainBeamLights, final boolean hasBlinkingLights) {
        super(id);
        this.name = name;
        this.transmissionConfig = transmissionConfig;
        this.numOfGears = numOfGears;
        this.hasMainBeamLights = hasMainBeamLights;
        this.hasBlinkingLights = hasBlinkingLights;
    }

    //---- Methods ----
    public String getName() {
        return name;
    }

    public TransmissionConfig getTransmissionConfig() {
        return transmissionConfig;
    }

    public int getNumOfGears() {
        return numOfGears;
    }

    public boolean hasMainBeamLights() {
        return hasMainBeamLights;
    }

    public boolean hasBlinkingLights() {
        return hasBlinkingLights;
    }
}
