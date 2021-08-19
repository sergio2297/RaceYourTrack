package model.settings.configurable;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;

public class PedalsConfig extends ConfigurableProperty {

    //---- Constants and Definitions ----
    public static final PedalsConfig UNKNOWN = new PedalsConfig('U', RaceYourTrackApplication.getContext().getResources().getString(R.string.unknown));
    public static final PedalsConfig ARROWS = new PedalsConfig('A', RaceYourTrackApplication.getContext().getResources().getString(R.string.arrows));
    public static final PedalsConfig BOTH = new PedalsConfig('B', RaceYourTrackApplication.getContext().getResources().getString(R.string.both));
    public static final PedalsConfig ADVANCED = new PedalsConfig('T', RaceYourTrackApplication.getContext().getResources().getString(R.string.advanced_throttle));

    private static final PedalsConfig[] VALUES = new PedalsConfig[] {UNKNOWN, ARROWS, BOTH, ADVANCED};
    private static final PedalsConfig[] SELECTABLE_VALUES = new PedalsConfig[] {ARROWS, BOTH, ADVANCED};

    //---- Construction ----
    private PedalsConfig(final char abbreviation, final String name) {
        super(abbreviation, name);
    }

    //---- Methods ----
    public static PedalsConfig[] getValues() {
        return VALUES;
    }

    public static PedalsConfig[] getUserSelectableValues() {
        return SELECTABLE_VALUES;
    }

    public static PedalsConfig valueOf(final String name) {
        if(name.equalsIgnoreCase(UNKNOWN.getName())) {
            return UNKNOWN;
        } else if(name.equalsIgnoreCase(ARROWS.getName())) {
            return ARROWS;
        } else if(name.equalsIgnoreCase(BOTH.getName())) {
            return BOTH;
        } else if(name.equalsIgnoreCase(ADVANCED.getName())) {
            return ADVANCED;
        } else {
            throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
    }
}
