package utils.viewComponents;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import es.sfernandez.raceyourtrack.R;

public class MsgDialogC extends DialogC {

    //---- Attributes ----
    private final String title, msg, btnText;
    private int btnDrawable;

    //---- View Elements ----
    private TextView txtTitle, txtMsg;
    private Button btnConfirm;

    //---- Constructor ----
    public MsgDialogC(final String title, final String msg, final String btnText) {
        this(null, title, msg, btnText, R.drawable.raceyourtrack_button);
    }

    public MsgDialogC(final String title, final String msg, final String btnText, final int btnDrawable) {
        this(null, title, msg, btnText, btnDrawable);
    }

    public MsgDialogC(CustomDialogListener listener, final String title, final String msg, final String btnText, final int btnDrawable) {
        super(listener);
        this.title = title;
        this.msg = msg;
        this.btnText = btnText;
        this.btnDrawable = btnDrawable;
    }

    //---- Methods ----
    protected View generateCustomDialogView() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_msg, null);

        txtTitle = view.findViewById(R.id.txt_dialog_title);
        txtTitle.setText(title);

        txtMsg = view.findViewById(R.id.txt_dialog_msg);
        txtMsg.setText(msg);

        btnConfirm = view.findViewById(R.id.btn_affirmative);
        btnConfirm.setText(btnText);
        btnConfirm.setBackgroundResource(btnDrawable);

        return view;
    }

    @Override
    protected void setListenerToElements(View dialogView) {
        btnConfirm.setOnClickListener( e -> {
            listener.onClick();
        });
    }

}
