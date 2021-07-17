package utils.viewComponents;

import android.widget.SeekBar;

/**
 * This custom component implements an horizontal SeekBar which always returns to the middle position when
 * the bar is released, like a steering wheel would do.
 */
public class SeekBarSteeringWheelC extends SeekBarReturnToIniC {

    //---- Constants and Definitions ----

    //---- Attributes ----

    //---- Construction ----
    public SeekBarSteeringWheelC(final SeekBar seekBar) {
        super(seekBar, 90, 10);
    }

    //---- Methods ----

}
