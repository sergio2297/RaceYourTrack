package model.raceway;

import androidx.annotation.DrawableRes;

import es.sfernandez.raceyourtrack.R;

public enum PieceType {
    STRAIGHT(R.drawable.png_straight), CURVE(R.drawable.png_curve);

    //---- Attributes ----
    @DrawableRes
    private final int drawable;

    //---- Construction ----
    private PieceType(final int drawableResource) {
        this.drawable = drawableResource;
    }
}
