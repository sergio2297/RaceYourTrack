package model.raceway;

import com.google.gson.annotations.SerializedName;

public class Piece {

    //---- Constants and Definitions ----

    //---- Attributes ----
    @SerializedName("x")
    private final int x;

    @SerializedName("y")
    private final int y;

    @SerializedName("rotation")
    private final int rotation;

    @SerializedName("piece")
    private final PieceType piece;

    //---- Constructor ----

    public Piece(final int x, final int y, final int rotation, final PieceType piece) {
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

    public PieceType getPiece() {
        return piece;
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
