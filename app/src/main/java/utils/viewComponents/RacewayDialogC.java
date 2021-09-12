package utils.viewComponents;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;

import es.sfernandez.raceyourtrack.R;
import model.raceway.Raceway;
import utils.Utils;

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
    }

    //---- Methods ----
    protected View generateCustomDialogView() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_full_raceway, null);
        gridLayout = view.findViewById(R.id.grid_layout_raceway_template_dialog);
        Utils.constructRacewayOnGrid(this.getContext(), raceway, gridLayout, cellSidePx, true);

        return view;
    }

    @Override
    protected void setListenerToElements(View dialogView) {}

}
