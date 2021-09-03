package es.sfernandez.raceyourtrack.play.racewayBuilding;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import model.raceway.Raceway;

public class RacewayGuideBuildingActivity extends AppCompatActivity {

    //---- Constants and Definitions ----
    private final float BUTTONS_HEIGHT_PERCENTAGE = 0.15f, MATERIALS_WIDTH_PERCENTAGE = 0.25f;

    //---- Attributes ----
    private Raceway raceway;
    private int cellSidePx, buttonsHeightPx, materialsWidthPx;

    //---- View Elements ----
    private GridLayout gridLayout;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        raceway = (Raceway) getIntent().getSerializableExtra("raceway");
        calculateDimensions(raceway);

        loadViewElements();
    }

    private void loadViewElements() {
        setContentView(R.layout.activity_raceway_guide_building);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, buttonsHeightPx);
        layoutParams.height = buttonsHeightPx;
        layoutParams.gravity = Gravity.CENTER;
        findViewById(R.id.lyt_buttons_raceway_guide_building).setLayoutParams(layoutParams);

        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(materialsWidthPx, ViewGroup.LayoutParams.MATCH_PARENT);
        txtParams.gravity = Gravity.CENTER;
        findViewById(R.id.lyt_materials_raceway_guide_building).setLayoutParams(txtParams);

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
     * attending to the number of cells and the dimensions of the other components
     */
    private void calculateDimensions(final Raceway raceway) {
        int numOfRowsOnScreen = raceway.getNumOfCellsPerRow(); //Is switched because the screen is landscape
        int numOfColumnsOnScreen = raceway.getNumOfCellsPerColumn();

        int screenWidth = RaceYourTrackApplication.getScreenWidthPx();
        int screenHeight = RaceYourTrackApplication.getScreenHeightPx();

        this.buttonsHeightPx = (int)(screenWidth * BUTTONS_HEIGHT_PERCENTAGE);
        this.materialsWidthPx = (int)(screenHeight * MATERIALS_WIDTH_PERCENTAGE);
        this.cellSidePx = Math.min((screenWidth - buttonsHeightPx) / numOfRowsOnScreen, (screenHeight - materialsWidthPx) / numOfColumnsOnScreen);
    }
}

