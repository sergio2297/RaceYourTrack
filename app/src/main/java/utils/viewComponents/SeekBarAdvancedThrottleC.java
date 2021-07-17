package utils.viewComponents;

import android.widget.SeekBar;

/**
 * This custom component implements an vertical SeekBar which always returns to the beginning position when
 * the bar is released, like a pedal would do.
 */
public class SeekBarAdvancedThrottleC extends SeekBarReturnToIniC {

    //---- Constants and Definitions ----

    //---- Attributes ----

    //---- Construction ----
    public SeekBarAdvancedThrottleC(final SeekBar seekBar) {
        super(seekBar, 0, 60);
    }

    //---- Methods ----

}
