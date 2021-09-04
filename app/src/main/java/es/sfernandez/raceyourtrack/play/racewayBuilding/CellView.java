package es.sfernandez.raceyourtrack.play.racewayBuilding;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;

import model.raceway.Cell;
import model.raceway.Piece;

public class CellView extends GridLayout {

    //---- Constants and Definitions ----
    private final int BUILDING_REPRESENTATION_MARGIN_PX = 10;

    //---- Attributes ----
    private final Cell cell;
    private final int pieceSidePx;
    private final boolean buildingRepresentation;

    //---- Construction ----
    public CellView(final Context context, final Cell cell, final int cellSide) {
        this(context, cell, cellSide, false);
    }

    public CellView(final Context context, final Cell cell, final int cellSide, final boolean buildingRepresentation) {
        super(context);
        this.cell = cell;
        this.buildingRepresentation = buildingRepresentation;
        int pieceSidePx = calculateCellDimension(cell, cellSide);
        this.pieceSidePx = buildingRepresentation ? pieceSidePx - BUILDING_REPRESENTATION_MARGIN_PX*2 : pieceSidePx;
        initializeViewElements();
    }

    //---- Methods ----
    private void initializeViewElements() {
        this.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        this.setColumnCount(cell.getNumOfPiecesPerColumn());
        this.setRowCount(cell.getNumOfPiecesPerRow());
        GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams();
        gridParam.setGravity(Gravity.CENTER);
        gridParam.setMargins(BUILDING_REPRESENTATION_MARGIN_PX, BUILDING_REPRESENTATION_MARGIN_PX,
                BUILDING_REPRESENTATION_MARGIN_PX, BUILDING_REPRESENTATION_MARGIN_PX);
        this.setLayoutParams(gridParam);

        for(int i = 0; i < this.getColumnCount() * this.getRowCount(); ++i) {
            int row = i/cell.getNumOfPiecesPerColumn();
            int col = i%cell.getNumOfPiecesPerColumn();
            Piece piece = cell.getPieces()[row][col];

            ImageView imageView = new ImageView(getContext());
            if(piece.getPieceType() != Piece.Type.NONE) {
                imageView.setBackground(piece.getPieceDrawable(getContext()));
                imageView.setRotation(piece.getRotation() * 90);
            }
            this.addView(imageView, i);

            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = pieceSidePx;
            param.width = pieceSidePx;
            param.setGravity(Gravity.CENTER);
            if(buildingRepresentation) {
                param.setMargins(BUILDING_REPRESENTATION_MARGIN_PX, BUILDING_REPRESENTATION_MARGIN_PX,
                        BUILDING_REPRESENTATION_MARGIN_PX, BUILDING_REPRESENTATION_MARGIN_PX);
            }
            param.columnSpec = GridLayout.spec(i%cell.getNumOfPiecesPerRow(), 1);
            param.rowSpec = GridLayout.spec(i/cell.getNumOfPiecesPerRow(), 1);
            imageView.setLayoutParams(param);
        }
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
