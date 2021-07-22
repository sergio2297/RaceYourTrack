package utils.viewComponents;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import es.sfernandez.raceyourtrack.R;

/**
 * IndeterminateProgressDialogC offer the possibility to execute some work in background while a
 * Dialog is showing the user the current state with messages.
 * Normally, the service to execute will be the start() method of a Thread.
 */
public class IndeterminateProgressDialogC extends DialogFragment {

    //---- Constants and Definitions ----
    public interface IndeterminateProgressDialogService {
        public void work();
    }

    //---- Attributes ----
    private String title, msg;
    private IndeterminateProgressDialogService service;

    private ProgressBar progressBar;
    private TextView txtTitle, txtMessage;

    //---- Constructor ----
    public IndeterminateProgressDialogC(final String title, final String msg, final IndeterminateProgressDialogService service) {
        super();
        this.title = title;
        this.msg = msg;
        this.service = service;
    }

    //---- Dialog Methods ----
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        this.setCancelable(false);
        View dialogView = this.generateDialogView();

        // Add the view to the builder and create it
        builder.setView(dialogView);
        return builder.create();
    }

    private View generateDialogView() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_indeterminate_progress, null);

        // Load view components
        progressBar = view.findViewById(R.id.progress_indeterminate);
        txtTitle = view.findViewById(R.id.txt_title_progress_dialog);
        txtTitle.setText(title);
        txtMessage = view.findViewById(R.id.txt_msg_progress_dialog);
        txtMessage.setText(msg);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        service.work();
    }

    //---- Methods ----
    @Override
    public void show(final FragmentManager fragmentManager, final String tag) {
        super.showNow(fragmentManager, tag);
    }

    public void setTitle(final String title) {
        txtTitle.setText(title);
    }

    public void setMessage(final String msg) {
        txtMessage.setText(msg);
        Log.i(this.getTag(), msg);
    }

}
