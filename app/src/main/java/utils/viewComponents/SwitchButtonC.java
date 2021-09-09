package utils.viewComponents;

import android.view.View;
import android.widget.Button;

/**
 * This custom component performance the behaviour os a switch button. Meaning, the button have two states.
 * When the button is pressed it will change to the state A. It will be necessary to press it again to
 * make it transit to the B state. Nothing special happens when the button is leaving touching.
 *
 * Ex.: Emergency lights.
 */
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
    private boolean isSelected;
    private OnActivateListener onActivateListener;
    private OnDeactivateListener onDeactivateListener;

    //---- Construction ----
    public SwitchButtonC(final Button btn) {
        this.button = btn;
        this.button.setOnClickListener(e -> {
            if(isSelected && onDeactivateListener != null) {
                onDeactivateListener.onDeactivate();
            } else if(onActivateListener != null) {
                onActivateListener.onActivate();
            }
            isSelected = !isSelected;
            button.setSelected(isSelected);
        });
        this.isSelected = false;
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

    public void setText(final String text) {
        this.button.setText(text);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void select() {
        isSelected = true;
        button.setSelected(isSelected);
    }

    public void deselect() {
        isSelected = false;
        button.setSelected(isSelected);
    }

    public void click() {
        button.performClick();
    }
}
