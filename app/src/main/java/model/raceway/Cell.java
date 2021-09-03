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

    private int[] piecesCount;

    //---- Construction
    public Cell(final int x, final int y, final int order, final Piece[][] pieces) {
        this.x = x;
        this.y = y;
        this.order = order;
        this.pieces = pieces;
    }

    //---- Methods ----
    public void initPiecesCount() {
        this.piecesCount = new int[Piece.Type.values().length - 1];
        for(int i = 0; i < pieces.length; ++i) {
            for(int j = 0; j < pieces[i].length; ++j) {
                Piece.Type pieceType = pieces[i][j].getPieceType();
                if(pieceType != Piece.Type.NONE) {
                    ++piecesCount[pieceType.ordinal()];
                }
            }
        }
    }

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

    public int[] getPiecesCount() {
        return piecesCount;
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
