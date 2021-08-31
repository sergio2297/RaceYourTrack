package model.raceway;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import es.sfernandez.raceyourtrack.R;

public class Piece implements Serializable {

    //---- Constants and Definitions ----
    public enum Type {
        STRAIGHT(R.drawable.png_straight), START_STRAIGHT(R.drawable.png_start_straight), GOAL_STRAIGHT(R.drawable.png_goal_straight), SPECIAL_CHECK_STRAIGHT(R.drawable.png_special_check_straight),
        CURVE(R.drawable.png_curve), SPECIAL_CHECK_CURVE(R.drawable.png_special_check_curve), NONE(-1);

        //---- Attributes ----
        @DrawableRes
        private final int drawableId;

        //---- Construction ----
        private Type(final int drawableResource) {
            this.drawableId = drawableResource;
        }

        //---- Methods ----
        public int getDrawableId() {
            return drawableId;
        }
    }

    //---- Attributes ----
    @SerializedName("x")
    private final int x;

    @SerializedName("y")
    private final int y;

    @SerializedName("rotation")
    private final int rotation;

    @SerializedName("piece")
    private final Type piece;

    //---- Constructor ----
    public Piece(final int x, final int y, final int rotation, final Type piece) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.piece = piece;
    }

    //---- Methods ----
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRotation() {
        return rotation;
    }

    public Type getPiece() {
        return piece;
    }

    public Drawable getPieceDrawable(Context context) {
        return context.getDrawable(piece.getDrawableId());
    }

    @Override
    public String toString() {
        return "Piece{" +
                "x=" + x +
                ", y=" + y +
                ", rotation=" + rotation +
                ", piece=" + piece +
                '}';
    }
}
