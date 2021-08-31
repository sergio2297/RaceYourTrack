package model.raceway;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class Cell implements Serializable {

    //---- Attributes ----
    @SerializedName("x")
    private final int x;

    @SerializedName("y")
    private final int y;

    @SerializedName("order")
    private final int order;

    @SerializedName("pieces")
    private final Piece[][] pieces;

    //---- Construction
    public Cell(final int x, final int y, final int order, final Piece[][] pieces) {
        this.x = x;
        this.y = y;
        this.order = order;
        this.pieces = pieces;
    }

    //---- Methods ----
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOrder() {
        return order;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public int getNumOfPiecesPerColumn() {
        return pieces[0].length;
    }

    public int getNumOfPiecesPerRow() {
        return pieces.length;
    }

    @Override
    public String toString() {
        return "Cells{" +
                "x=" + x +
                ", y=" + y +
                ", order=" + order +
                ", pieces=" + Arrays.deepToString(pieces) +
                '}';
    }

}
