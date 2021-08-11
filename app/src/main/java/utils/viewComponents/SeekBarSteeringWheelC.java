package utils.viewComponents;

import android.graphics.drawable.RotateDrawable;
import android.widget.SeekBar;

/**
 * This custom component implements an horizontal SeekBar which always returns to the middle position when
 * the bar is released, like a steering wheel would do.
 */
public class SeekBarSteeringWheelC extends SeekBarReturnToIniC {

    //---- Constants and Definitions ----

    //---- Attributes ----
    private final RotateDrawable steeringWheelDrawable;

    //---- Construction ----
    public SeekBarSteeringWheelC(final SeekBar seekBar) {
        super(seekBar, 90, 10);

        this.steeringWheelDrawable = (RotateDrawable) seekBar.getBackground();
        this.steeringWheelDrawable.setLevel(calculateLevelOfRotation(90));

        this.addListener(new OnProgressChangeListener() {
            @Override
            public void onProgressChange(int progress) {
                steeringWheelDrawable.setLevel(calculateLevelOfRotation(progress));
            }
        });
    }

    //---- Methods ----

    /**
     * The setLevel method from RotateDrawable admits value in a range of [0, 10000], so it's
     * necessary to calculate the relation between that range, and the seekbar range. Also, the
     * RotationDrawable must have the same range between (from-to degrees) than the seekbar to
     * get a normal behaviour.
     */
    private int calculateLevelOfRotation(final int progress) {
        return progress*(10000 / 180);
    }
}
