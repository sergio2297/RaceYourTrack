package model.settings.configurable;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;

public class DrivingModeConfig extends ConfigurableProperty {

    //---- Constants and Definitions ----
    public static final DrivingModeConfig CUSTOM = new DrivingModeConfig('C', RaceYourTrackApplication.getContext().getResources().getString(R.string.custom), SteeringConfig.UNKNOWN, TransmissionConfig.UNKNOWN, PedalsConfig.UNKNOWN);
    public static final DrivingModeConfig BASIC = new DrivingModeConfig('B', RaceYourTrackApplication.getContext().getResources().getString(R.string.beginner), SteeringConfig.ARROWS, TransmissionConfig.AUTOMATIC, PedalsConfig.ARROWS);
    public static final DrivingModeConfig REALISTIC = new DrivingModeConfig('R', RaceYourTrackApplication.getContext().getResources().getString(R.string.realistic), SteeringConfig.STEERING_WHEEL, TransmissionConfig.MANUAL, PedalsConfig.BOTH);
    public static final DrivingModeConfig SENSATIONAL = new DrivingModeConfig('S', RaceYourTrackApplication.getContext().getResources().getString(R.string.race_driver), SteeringConfig.STEERING_WHEEL, TransmissionConfig.AUTOMATIC, PedalsConfig.ADVANCED);

    private static final DrivingModeConfig[] VALUES = new DrivingModeConfig[] {CUSTOM, BASIC, REALISTIC, SENSATIONAL};
    private static final DrivingModeConfig[] SELECTABLE_VALUES = new DrivingModeConfig[] {CUSTOM, BASIC, REALISTIC, SENSATIONAL};

    //---- Attributes ----
    private final SteeringConfig steeringConfig;
    private final TransmissionConfig transmissionConfig;
    private final PedalsConfig pedalsConfig;

    //---- Constructor ----
    private DrivingModeConfig(final char abbreviation, final String name,
                              final SteeringConfig steeringConfig, final TransmissionConfig transmissionConfig,
                              final PedalsConfig pedalsConfig) {
        super(abbreviation, name);
        this.steeringConfig = steeringConfig;
        this.transmissionConfig = transmissionConfig;
        this.pedalsConfig = pedalsConfig;
    }

    //---- Methods ----
    public SteeringConfig getSteeringConfig() {
        return steeringConfig;
    }

    public TransmissionConfig getTransmissionConfig() {
        return transmissionConfig;
    }

    public PedalsConfig getPedalsConfig() {
        return pedalsConfig;
    }

    public static DrivingModeConfig[] getValues() {
        return VALUES;
    }

    public static DrivingModeConfig[] getUserSelectableValues() {
        return SELECTABLE_VALUES;
    }

    public static DrivingModeConfig valueOf(final String name) {
        if(name.equalsIgnoreCase(CUSTOM.getName())) {
            return CUSTOM;
        } else if(name.equalsIgnoreCase(BASIC.getName())) {
            return BASIC;
        } else if(name.equalsIgnoreCase(REALISTIC.getName())) {
            return REALISTIC;
        } else if(name.equalsIgnoreCase(SENSATIONAL.getName())) {
            return SENSATIONAL;
        } else {
            throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
    }
}
