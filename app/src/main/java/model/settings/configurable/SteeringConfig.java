package model.settings.configurable;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHelper;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;

public class SteeringConfig extends ConfigurableProperty {

    //---- Constants and Definitions ----
    public static final SteeringConfig UNKNOWN = new SteeringConfig('U', RaceYourTrackApplication.getContext().getResources().getString(R.string.unknown));
    public static final SteeringConfig ARROWS = new SteeringConfig('A', RaceYourTrackApplication.getContext().getResources().getString(R.string.arrows));
    public static final SteeringConfig STEERING_WHEEL = new SteeringConfig('S', RaceYourTrackApplication.getContext().getResources().getString(R.string.steering_wheel));

    private static final SteeringConfig[] VALUES = new SteeringConfig[] {UNKNOWN, ARROWS, STEERING_WHEEL};
    private static final SteeringConfig[] SELECTABLE_VALUES = new SteeringConfig[] {ARROWS, STEERING_WHEEL};

    //---- Construction ----
    private SteeringConfig(final char abbreviation, final String name) {
        super(abbreviation, name);
    }

    //---- Methods ----
    public static SteeringConfig[] getValues() {
        return VALUES;
    }

    public static SteeringConfig[] getUserSelectableValues() {
        return SELECTABLE_VALUES;
    }

    public static SteeringConfig valueOf(final String name) {
        if(name.equalsIgnoreCase(UNKNOWN.getName())) {
            return UNKNOWN;
        } else if(name.equalsIgnoreCase(ARROWS.getName())) {
            return ARROWS;
        } else if(name.equalsIgnoreCase(STEERING_WHEEL.getName())) {
            return STEERING_WHEEL;
        } else {
            throw new AppUnCatchableException(new AppError(AppErrorHelper.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
    }
}
