package es.sfernandez.raceyourtrack.play.racewayBuilding;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import model.raceway.Raceway;

public class RacewayGuideBuildingActivity extends AppCompatActivity {

    //---- Attributes ----
    private Raceway raceway;
    private int cellSidePx;

    //---- View Elements ----
    private GridLayout gridLayout;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        raceway = (Raceway) getIntent().getSerializableExtra("raceway");
        cellSidePx = calculateCellDimension(raceway);

        loadViewElements();
    }

    private void loadViewElements() {
        setContentView(R.layout.activity_raceway_guide_building);
        gridLayout = findViewById(R.id.grid_layout_raceway_template);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(raceway.getNumOfCellsPerColumn());
        gridLayout.setRowCount(raceway.getNumOfCellsPerRow());

        for(int i = 0; i < gridLayout.getColumnCount() * gridLayout.getRowCount(); ++i) {
            int row = i/raceway.getNumOfCellsPerColumn();
            int col = i%raceway.getNumOfCellsPerColumn();
            CellView cellView = new CellView(this, raceway.getCells()[row][col], cellSidePx);

            gridLayout.addView(cellView, i);

            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = cellSidePx;
            param.width = cellSidePx;
            param.setGravity(Gravity.CENTER);
            param.rowSpec = GridLayout.spec(row, 1);
            param.columnSpec = GridLayout.spec(col, 1);
            cellView.setLayoutParams(param);
        }
    }

    //---- Methods ----
    /**
     * All cells must be square, so it will calculate the max square possible for the current device
     * attending to the number of cells
     */
    private int calculateCellDimension(final Raceway raceway) {
        int numOfColumns = raceway.getNumOfCellsPerRow();
        int numOfRows = raceway.getNumOfCellsPerColumn();

        int screenWidth = RaceYourTrackApplication.getScreenWidthPx();
        int screenHeight = RaceYourTrackApplication.getScreenHeightPx();

        return Math.min(screenHeight / numOfRows, screenWidth / numOfColumns);
    }
}

