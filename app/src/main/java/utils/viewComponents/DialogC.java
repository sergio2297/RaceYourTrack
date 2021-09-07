package utils.viewComponents;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public abstract class DialogC extends DialogFragment {

    //---- Constants and Definitions ----
    public interface CustomDialogListener {
        void onClick();
    }

    //---- Attributes ----
    protected CustomDialogListener listener;
    private DialogInterface.OnDismissListener dismissListener;

    //---- Constructor ----
    public DialogC(CustomDialogListener listener) {
        super();
        this.listener = listener;
    }

    //---- Methods ----
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Generamos la view del CustomDialog
        View dialogView = this.generateCustomDialogView();

        // Añadimos el listener necesario a los elementos de la View
        this.setListenerToElements(dialogView);

        // Añadimos el View al builder y lo creamos
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }

    protected abstract View generateCustomDialogView();
    protected abstract void setListenerToElements(View dialogView);

    public void setListener(final CustomDialogListener listener) {
        this.listener = listener;
    }
    public void setDismissListener(final DialogInterface.OnDismissListener listener) {
        this.dismissListener = dismissListener;
    }
}
