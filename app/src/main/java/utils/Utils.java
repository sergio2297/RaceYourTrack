package utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;

import androidx.fragment.app.FragmentManager;

import java.io.IOException;
import java.io.InputStream;

import es.sfernandez.raceyourtrack.play.racewayBuilding.CellView;
import model.raceway.Raceway;
import utils.viewComponents.MsgDialogC;

public abstract class Utils {

    /**
     * @return true iff obt is a Number 1 or a String "true".ignoreCase()
     */
    public static Boolean parseToBoolean(final Object obj) {
        if(obj instanceof Number && ((Number)obj).intValue() == 1
                || obj instanceof String && ((String)obj).equalsIgnoreCase("true")) {
            return true;
        }

        if(obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }

        return false;
    }

    public static void showMessageDialog(final String title, final String msg, final String btnText, final FragmentManager fragmentManager, final String tag) {
        final MsgDialogC dialog = new MsgDialogC(title, msg, btnText);
        dialog.setListener(() -> dialog.dismiss());
        dialog.show(fragmentManager, tag);
    }

    /**
     * Return the file's content which name is 'filename' (it must be in assets folder) in a
     * String object.
     */
    public static String getJsonStringFromAssets(final Context context, final String filename) {
        String jsonString = "";
        try {
            InputStream in = context.getAssets().open(filename);

            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    /**
     * Print the raceway in the gridLayout passed as argument
     * @param context
     * @param raceway
     * @param gridLayout
     * @param cellSidePx
     * @param buildingRepresentation
     */
    public static void constructRacewayOnGrid(final Context context, final Raceway raceway, final GridLayout gridLayout, final int cellSidePx, final boolean buildingRepresentation) {
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(raceway.getNumOfCellsPerColumn());
        gridLayout.setRowCount(raceway.getNumOfCellsPerRow());

        for(int i = 0; i < gridLayout.getColumnCount() * gridLayout.getRowCount(); ++i) {
            int row = i/raceway.getNumOfCellsPerColumn();
            int col = i%raceway.getNumOfCellsPerColumn();
            CellView cellView = new CellView(context, raceway.getCells()[row][col], cellSidePx, buildingRepresentation);

            gridLayout.addView(cellView, i);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = cellSidePx;
            param.width = cellSidePx;
            param.setGravity(Gravity.CENTER);
            param.rowSpec = GridLayout.spec(row, 1);
            param.columnSpec = GridLayout.spec(col, 1);
            cellView.setLayoutParams(param);
        }
    }

}
