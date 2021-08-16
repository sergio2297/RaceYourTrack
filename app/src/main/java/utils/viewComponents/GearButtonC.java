package utils.viewComponents;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * This custom component performance the behaviour of a normal button. Meaning, the button do something
 * when the button is pressed.
 *
 * Ex.: cams.
 */
public class GearButtonC {

    //---- Constants and Definitions ----
    public interface OnPressListener {
        void onButtonPressed();
    }

    // https://stackoverflow.com/questions/22606977/how-can-i-get-button-pressed-time-when-i-holding-button-on

    //---- Attributes ----
    private final Button button;
    private OnPressListener onPressListener;

    //---- Construction ----
    public GearButtonC(final Button btn) {
        this.button = btn;
        this.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        onPressListener.onButtonPressed();
                        break;
                }
                return false;
            }
        });
    }

    //---- Methods ----
    public void setOnPressListener(final OnPressListener listener) {
        this.onPressListener = listener;
    }

    public void setVisible(final boolean visible, final boolean gone) {
        this.button.setVisibility( visible ? View.VISIBLE : gone ? View.GONE : View.INVISIBLE);
    }

    public boolean isVisible() {
        return button.getVisibility() == Button.VISIBLE ? true : false;
    }
}
