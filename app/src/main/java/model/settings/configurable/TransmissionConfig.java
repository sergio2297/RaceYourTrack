package model.settings.configurable;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;

public class TransmissionConfig extends ConfigurableProperty {

    //---- Constants and Definitions ----
    public static final TransmissionConfig UNKNOWN = new TransmissionConfig('U', RaceYourTrackApplication.getContext().getResources().getString(R.string.unknown));
    public static final TransmissionConfig MANUAL = new TransmissionConfig('M', RaceYourTrackApplication.getContext().getResources().getString(R.string.manual));
    public static final TransmissionConfig H_SHIFT = new TransmissionConfig('H', RaceYourTrackApplication.getContext().getResources().getString(R.string.h_shift));
    public static final TransmissionConfig SEQUENTIAL_SHIFT = new TransmissionConfig('S', RaceYourTrackApplication.getContext().getResources().getString(R.string.sequential_shift));
    public static final TransmissionConfig AUTOMATIC = new TransmissionConfig('A', RaceYourTrackApplication.getContext().getResources().getString(R.string.automatic));

    private static final TransmissionConfig[] VALUES = new TransmissionConfig[] {UNKNOWN, MANUAL, H_SHIFT, SEQUENTIAL_SHIFT, AUTOMATIC};
    private static final TransmissionConfig[] SELECTABLE_VALUES = new TransmissionConfig[] {MANUAL, AUTOMATIC};

    //---- Construction ----
    private TransmissionConfig(final char abbreviation, final String name) {
        super(abbreviation, name);
    }

    //---- Methods ----
    public static TransmissionConfig[] getValues() {
        return VALUES;
    }

    public static TransmissionConfig[] getUserSelectableValues() {
        return SELECTABLE_VALUES;
    }

    public static TransmissionConfig valueOf(final String name) {
        if(name.equalsIgnoreCase(UNKNOWN.getName())) {
            return UNKNOWN;
        } else if(name.equalsIgnoreCase(MANUAL.getName())) {
            return MANUAL;
        } else if(name.equalsIgnoreCase(H_SHIFT.getName())) {
            return H_SHIFT;
        } else if(name.equalsIgnoreCase(SEQUENTIAL_SHIFT.getName())) {
            return SEQUENTIAL_SHIFT;
        } else if(name.equalsIgnoreCase(AUTOMATIC.getName())) {
            return AUTOMATIC;
        } else {
            throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
    }
}
