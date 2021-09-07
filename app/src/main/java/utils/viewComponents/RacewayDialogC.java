package utils.viewComponents;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.play.racewayBuilding.CellView;
import model.raceway.Raceway;

public class RacewayDialogC extends DialogC {

    //---- Attributes ----
    private final Raceway raceway;
    private final int cellSidePx;

    //---- View Elements ----
    private GridLayout gridLayout;

    //---- Constructor ----
    public RacewayDialogC(final Raceway raceway, final int cellSidePx) {
        super(null);
        this.raceway = raceway;
        this.cellSidePx = cellSidePx;
//        this.cellSidePx = 60;
    }

    //---- Methods ----
    protected View generateCustomDialogView() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_full_raceway, null);

        gridLayout = view.findViewById(R.id.grid_layout_raceway_template_dialog);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(raceway.getNumOfCellsPerColumn());
        gridLayout.setRowCount(raceway.getNumOfCellsPerRow());

        for(int i = 0; i < gridLayout.getColumnCount() * gridLayout.getRowCount(); ++i) {
            int row = i/raceway.getNumOfCellsPerColumn();
            int col = i%raceway.getNumOfCellsPerColumn();
            CellView cellView = new CellView(this.getContext(), raceway.getCells()[row][col], cellSidePx, true);

            gridLayout.addView(cellView, i);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = cellSidePx;
            param.width = cellSidePx;
            param.setGravity(Gravity.CENTER);
            param.rowSpec = GridLayout.spec(row, 1);
            param.columnSpec = GridLayout.spec(col, 1);
            cellView.setLayoutParams(param);
        }

        return view;
    }

    @Override
    protected void setListenerToElements(View dialogView) {}

}
