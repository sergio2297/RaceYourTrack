package es.sfernandez.raceyourtrack.play.racewayBuilding;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.bluetoothConnection.BluetoothActivity;
import es.sfernandez.raceyourtrack.carController.ChallengeCarControllerActivity;
import model.Game;
import model.raceway.Cell;
import model.raceway.Raceway;
import utils.ObjectProperty;
import utils.viewComponents.MsgDialogC;
import utils.viewComponents.RacewayDialogC;

public class RacewayGuideBuildingActivity extends AppCompatActivity {

    //---- Constants and Definitions ----
    private final float BUTTONS_HEIGHT_PERCENTAGE = 0.15f, MATERIALS_WIDTH_PERCENTAGE = 0.25f;

    //---- Attributes ----
    private Raceway raceway;
    private int currentStep = -1;
    private ObjectProperty<Integer> currentPosition = new ObjectProperty<>(-1);
    private Cell[] sortedCells;
    private int cellSidePx, buttonsHeightPx, materialsWidthPx;
    private boolean buildFinished = false;
    private boolean startDriving = false;

    //---- View Elements ----
    private RecyclerView recyclerView;
    private TextView txtCurrentStep;
    private Button btnPrevious, btnNext, btnAction;
    private GridLayout gridLayout;

    //---- Activity Methods ----
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        raceway = (Raceway) getIntent().getSerializableExtra("raceway");
        raceway.initPiecesCount();
        calculateDimensions(raceway);
        this.sortedCells = raceway.getCellsSorted();

        loadViewElements();
        addListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerView != null) {
            recyclerView.setAdapter(new PiecesCountRecyclerViewAdapter(raceway.getPiecesCount()));
        }
    }

    @Override
    protected void onDestroy() {
//        if(startDriving) {
//            startDriving();
//        }
        super.onDestroy();
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

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = cellSidePx;
            param.width = cellSidePx;
            param.setGravity(Gravity.CENTER);
            param.rowSpec = GridLayout.spec(row, 1);
            param.columnSpec = GridLayout.spec(col, 1);
            cellView.setLayoutParams(param);
        }

        btnPrevious = findViewById(R.id.btn_raceway_building_previous_step);
        btnPrevious.setVisibility(View.INVISIBLE);
        txtCurrentStep = findViewById(R.id.txt_current_step_raceway_guide_building);
        btnNext = findViewById(R.id.btn_raceway_building_next_step);
        btnNext.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, buttonsHeightPx);
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.small_padding);
        layoutParams.height = buttonsHeightPx - layoutParams.topMargin;
        layoutParams.gravity = Gravity.CENTER;
        findViewById(R.id.lyt_buttons_raceway_guide_building).setLayoutParams(layoutParams);

        LinearLayout.LayoutParams piecesCountContainerParams = new LinearLayout.LayoutParams(materialsWidthPx, ViewGroup.LayoutParams.MATCH_PARENT);
        piecesCountContainerParams.gravity = Gravity.CENTER;
        findViewById(R.id.lyt_materials_raceway_guide_building).setLayoutParams(piecesCountContainerParams);

        View recyclerViewElement = findViewById(R.id.recyclerview_pieces_count_raceway_guide_building);
        if(recyclerViewElement instanceof RecyclerView) {
            this.recyclerView = (RecyclerView) recyclerViewElement;
            this.recyclerView.setAdapter(new PiecesCountRecyclerViewAdapter(raceway.getPiecesCount()));
            this.recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        }

        btnAction = findViewById(R.id.btn_action_raceway_guide_building);
    }

    private void addListeners() {
        currentPosition.addListener( (oldValue, newValue) -> {
            if(currentPosition.getProperty() < sortedCells.length) {
                loadCellInScreen(sortedCells[currentPosition.getProperty()]);
            }

            if(newValue.equals(new Integer(0))) {
                btnPrevious.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            } else if(buildFinished && newValue.equals(new Integer(sortedCells.length - 1))) {
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
            } else {
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        });

        btnAction.setOnClickListener( e -> {
            if(currentPosition.getProperty().equals(new Integer(-1))) { //Build
                currentPosition.setProperty(new Integer(0));
                currentStep = 0;
                btnAction.setText(R.string.show_full_raceway);
                btnAction.setBackground(getDrawable(R.drawable.raceyourtrack_button));
            } else if(buildFinished) {
                btnAction.setSelected(true);
                startDriving = true;
//                finish();
                startDriving();
            } else {
                showRacewayInDialog();
            }
        });

        btnNext.setOnClickListener( e -> {
            if(currentStep == currentPosition.getProperty() && currentStep != sortedCells.length) {
                if(currentStep + 1 != sortedCells.length) {
                    Game.getInstance().getSoundPlayer().playBuildSomethingSound();
                }
                ++currentStep;
            }
            currentPosition.setProperty(currentPosition.getProperty() + 1);

            if(!buildFinished && currentStep == sortedCells.length) {
                buildFinished = true;
                btnNext.setVisibility(View.INVISIBLE);
                btnAction.setText("");
                btnAction.setBackground(getDrawable(R.drawable.run_button));
                Game.getInstance().getSoundPlayer().playLapCheckSound();

                final MsgDialogC dialog = new MsgDialogC("Enhorabuena", "Has terminado de construir el " +
                        (raceway.getType() == Raceway.Type.CIRCUIT ? "circuito" : "tramo" ) + ".\n" +
                        "¡Ahora ve y conduce sobre él!", "", R.drawable.run_button);
                dialog.setListener(() -> {
                    dialog.dismiss();
                    startDriving = true;
//                    finish();
                    startDriving();
                });
                dialog.show(getSupportFragmentManager(), "Tag");
            }
        });

        btnPrevious.setOnClickListener( e -> {
            currentPosition.setProperty(currentPosition.getProperty() - 1);
        });
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

    private void loadCellInScreen(final Cell cell) {
        int screenWidth = RaceYourTrackApplication.getScreenWidthPx();
        int screenHeight = RaceYourTrackApplication.getScreenHeightPx();
        int cellSidePx = Math.min((screenWidth - buttonsHeightPx) / 1, (screenHeight - materialsWidthPx) / 1);

        CellView cellView = new CellView(this, cell, cellSidePx, true);

        gridLayout.removeAllViewsInLayout();

        gridLayout.setColumnCount(1);
        gridLayout.setRowCount(1);
        gridLayout.addView(cellView);

        this.txtCurrentStep.setText("Paso " + (currentPosition.getProperty() + 1) + "/" + sortedCells.length);
        this.recyclerView.setAdapter(new PiecesCountRecyclerViewAdapter(cell.getPiecesCount()));
    }

    private void showRacewayInDialog() {
        RacewayDialogC dialog = new RacewayDialogC(raceway, cellSidePx);
        dialog.show(getSupportFragmentManager(), "Tag");
    }

    private void startDriving() {
        Game.getInstance().getSoundPlayer().playCarPassingAwaySound();
        if(!Game.getInstance().isCarConnected()) {
            Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
            intent.putExtra("targetActivity", ChallengeCarControllerActivity.class);
            getApplicationContext().startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), ChallengeCarControllerActivity.class);
            getApplicationContext().startActivity(intent);
        }
    }
}

