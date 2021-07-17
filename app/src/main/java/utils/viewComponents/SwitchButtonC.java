package utils.viewComponents;

import android.view.View;
import android.widget.Button;

// TODO: Pasar el estilo que debe tener el boton en cada uno de los estados
public class SwitchButtonC {

    //---- Constants and Definitions ----
    public interface OnActivateListener {
        void onActivate();
    }
    public interface OnDeactivateListener {
        void onDeactivate();
    }

    //---- Attributes ----
    private final Button button;
    private boolean isActive;
    private OnActivateListener onActivateListener;
    private OnDeactivateListener onDeactivateListener;

    //---- Construction ----
    public SwitchButtonC(final Button btn) {
        this.button = btn;
        this.button.setOnClickListener(e -> {
            if(isActive) {
                onDeactivateListener.onDeactivate();
            } else {
                onActivateListener.onActivate();
            }
            isActive = !isActive;
        });
        this.isActive = false;
    }

    //---- Methods ----
    public void setOnActivateListener(final OnActivateListener listener) {
        this.onActivateListener = listener;
    }

    public void setOnDeactivateListener(final OnDeactivateListener listener) {
        this.onDeactivateListener = listener;
    }

    public void setVisible(boolean visible) {
        this.button.setVisibility( visible ? View.VISIBLE : View.INVISIBLE);
    }

    public boolean isVisible() {
        return button.getVisibility() == Button.VISIBLE ? true : false;
    }
}
