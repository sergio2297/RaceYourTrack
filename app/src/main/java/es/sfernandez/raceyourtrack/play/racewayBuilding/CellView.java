package es.sfernandez.raceyourtrack.play.racewayBuilding;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;

import model.raceway.Cell;
import model.raceway.Piece;

public class CellView extends GridLayout {

    //---- Attributes ----
    private final Cell cell;
    private final int pieceSidePx;

    //---- Construction ----
    public CellView(final Context context, final Cell cell, final int cellSide) {
        super(context);
        this.cell = cell;
        this.pieceSidePx = calculateCellDimension(cell, cellSide);
        initializeViewElements();
    }

    //---- Methods ----
    private void initializeViewElements() {
        this.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        this.setColumnCount(cell.getNumOfPiecesPerColumn());
        this.setRowCount(cell.getNumOfPiecesPerRow());

        for(int i = 0; i < this.getColumnCount() * this.getRowCount(); ++i) {
            int row = i/cell.getNumOfPiecesPerColumn();
            int col = i%cell.getNumOfPiecesPerColumn();
            Piece piece = cell.getPieces()[row][col];

            ImageView imageView = new ImageView(getContext());
            if(piece.getPiece() != Piece.Type.NONE) {
                imageView.setBackground(piece.getPieceDrawable(getContext()));
                imageView.setRotation(piece.getRotation() * 90);
            }
            this.addView(imageView, i);

            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = pieceSidePx;
            param.width = pieceSidePx;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(i%cell.getNumOfPiecesPerRow(), 1);
            param.rowSpec = GridLayout.spec(i/cell.getNumOfPiecesPerRow(), 1);
            imageView.setLayoutParams (param);
        }
    }

    private void addListenersToViewElements() {

    }

    /**
     * All pieces must be square, so it will calculate the max square possible for the current device
     * attending to the number of pieces in one cell
     */
    private int calculateCellDimension(final Cell cell, final int cellSide) {
        int numOfColumns = cell.getNumOfPiecesPerRow();
        int numOfRows = cell.getNumOfPiecesPerColumn();

        return Math.min(cellSide / numOfRows, cellSide / numOfColumns);
    }
}
