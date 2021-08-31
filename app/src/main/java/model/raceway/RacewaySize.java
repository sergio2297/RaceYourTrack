package model.raceway;

public enum RacewaySize {
    SMALL(1,1), NORMAL(2,2), LARGE(3,3);

    //---- Attributes ----
    private final int numPiecesPerCellX, numPiecesPerCellY;

    //---- Construction ----
    private RacewaySize(final int numPiecesPerCellX, final int numPiecesPerCellY) {
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
