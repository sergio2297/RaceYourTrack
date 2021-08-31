package model.raceway;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;
import utils.Utils;

public class Raceway implements Serializable {

    //---- Definitions and Constants ----
    public enum Type {
        CIRCUIT, TRACK, OTHER;
    }

    public enum Dimension {
        SMALL(1,1), NORMAL(2,2), LARGE(3,3);

        //---- Attributes ----
        private final int numPiecesPerCellX, numPiecesPerCellY;

        //---- Construction ----
        private Dimension(final int numPiecesPerCellX, final int numPiecesPerCellY) {
            this.numPiecesPerCellX = numPiecesPerCellX;
            this.numPiecesPerCellY = numPiecesPerCellY;
        }

        public int getNumPiecesPerCellX() {
            return numPiecesPerCellX;
        }

        public int getNumPiecesPerCellY() {
            return numPiecesPerCellY;
        }

        @Override
        public String toString() {
            return "RacewaySize{" +
                    "numPiecesPerCellsX=" + numPiecesPerCellX +
                    ", numPiecesPerCellsY=" + numPiecesPerCellY +
                    '}';
        }
    }

    private final int NUM_HORIZONTAL_CELLS = 3, NUM_VERTICAL_CELLS = 2;

    //---- Attributes ----
    @SerializedName("type")
    private final Type type;

    @SerializedName("hasSecret")
    private final boolean hasSecret;

    @SerializedName("name")
    private final String name;

    @SerializedName("description")
    private final String description;

    @SerializedName("size")
    private final Dimension size;

    @SerializedName("cells")
    private final Cell[][] cells;

    //---- Construction ----
    public Raceway(final Type type, final boolean hasSecret, final String name, final String description, final Dimension size, final Cell[][] cells) {
        this.type = type;
        this.hasSecret = hasSecret;
        this.name = name;
        this.description = description;
        this.size = size;
        this.cells = cells;
    }

    //---- Methods ----
    public Type getType() {
        return type;
    }

    public boolean isHasSecret() {
        return hasSecret;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Dimension getSize() {
        return size;
    }

    public int getNumOfCellsPerColumn() {
        return cells[0].length;
    }

    public int getNumOfCellsPerRow() {
        return cells.length;
    }

    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Checks if the Raceway Object is well build. If not, an AppError will be thrown
     */
    private boolean verify() {
        if(type == null || name == null || name.isEmpty()
                || description == null || description.isEmpty() || size == null) {
            throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_SOMETHING_NULL, "Raceways can't have any null attribute", RaceYourTrackApplication.getContext()));
        }

        if(cells == null || cells.length != NUM_VERTICAL_CELLS || cells[0].length != NUM_HORIZONTAL_CELLS) {
            throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_INCORRECT_CELLS_COUNT, "Raceways must have " + NUM_HORIZONTAL_CELLS + " x " + NUM_VERTICAL_CELLS + " cells", RaceYourTrackApplication.getContext()));
        } else {
            int numOfNoOrderedCells = 0, numOfSpecialPieces = 0;
            Set<Integer> setCaughtOrderNumber = new HashSet<>();
            for(int cell_row = 0; cell_row < cells.length; ++cell_row) {
                for(int cell_column = 0; cell_column < cells[cell_row].length; ++cell_column) {
                    Cell cell = cells[cell_row][cell_column];

                    if(cell.getPieces() == null) {
                        throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_CELLS_SOMETHING_NULL, "Cells can't have any null attribute", RaceYourTrackApplication.getContext()));
                    }

                    if(cell.getY() != cell_row || cell.getX() != cell_column) {
                        throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_INCORRECT_CELLS_LAYOUT, "Raceways must have cells ordered in its Cells matrix", RaceYourTrackApplication.getContext()));
                    }

                    if(cell.getOrder() == 0) {
                        ++numOfNoOrderedCells;
                    } else if(cell.getOrder() < 1 || cell.getOrder() > NUM_HORIZONTAL_CELLS*NUM_VERTICAL_CELLS) {
                        throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_CELLS_BAD_ORDERS, "Some cell has order out of bounds [1," + NUM_HORIZONTAL_CELLS*NUM_VERTICAL_CELLS + "]", RaceYourTrackApplication.getContext()));
                    } else {
                        setCaughtOrderNumber.add(new Integer(cell.getOrder()));
                    }

                    Piece[][] pieces = cell.getPieces();
                    if(pieces.length != size.getNumPiecesPerCellX() || pieces[0].length != size.getNumPiecesPerCellY()) {
                        throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_INCORRECT_PIECES_COUNT, "For this building cells must have " + size.getNumPiecesPerCellX() + " x " + size.getNumPiecesPerCellY() + " pieces", RaceYourTrackApplication.getContext()));
                    }

                    for(int piece_row = 0; piece_row < pieces.length; ++piece_row) {
                        for(int piece_column = 0; piece_column < pieces[0].length; ++piece_column) {
                            Piece piece = pieces[piece_row][piece_column];

                            if(piece.getPiece() == null) {
                                throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_PIECES_SOMETHING_NULL, "Pieces can't have any null attribute", RaceYourTrackApplication.getContext()));
                            }

                            if(piece.getY() != piece_row || piece.getX() != piece_column) {
                                throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_INCORRECT_PIECES_LAYOUT, "Cells must have pieces ordered in its Pieces matrix", RaceYourTrackApplication.getContext()));
                            }

                            if(piece.getPiece() == Piece.Type.SPECIAL_CHECK_STRAIGHT || piece.getPiece() == Piece.Type.SPECIAL_CHECK_CURVE) {
                                ++numOfSpecialPieces;
                            }
                        }
                    }

                    if((hasSecret && numOfSpecialPieces != 1) || (!hasSecret && numOfSpecialPieces != 0)) {
                        throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_INCORRECT_NUMBER_OF_SPECIAL_PIECES, "The number of special checks is incorrect. Provided: " + numOfSpecialPieces + " Expected: " + (hasSecret ? 1 : 0), RaceYourTrackApplication.getContext()));
                    }

                }
            }

            if((setCaughtOrderNumber.size() + numOfNoOrderedCells) != NUM_HORIZONTAL_CELLS*NUM_VERTICAL_CELLS) {
                throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.RACEWAY_BUILDING_CELLS_BAD_ORDERS, "Probably some cells has repeated orders", RaceYourTrackApplication.getContext()));
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Raceway{" +
                "type=" + type +
                ", hasSecret=" + hasSecret +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size.toString() +
                ", \ncells=" + Arrays.deepToString(cells) +
                '}';
    }

    /**
     * Build a Raceway from the json file which`s name is filename. The file must be placed in the
     * assets folder. The Raceway returned will be well built because it's verified before returns it
     */
    public static Raceway loadFromJson(final String filename) {
        String contentJson = Utils.getJsonStringFromAssets(RaceYourTrackApplication.getContext(), filename);
        Raceway raceway = new Gson().fromJson(contentJson, Raceway.class);
        raceway.verify();
        return raceway;
    }
}
