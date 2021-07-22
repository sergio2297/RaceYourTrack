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
public class PushButtonC {

    //---- Constants and Definitions ----
    public interface OnPressListener {
        void onButtonPressed();
    }
    public interface OnReleaseListener {
        void onButtonRelease();
    }

    // https://stackoverflow.com/questions/22606977/how-can-i-get-button-pressed-time-when-i-holding-button-on

    //---- Attributes ----
    private final Button button;
    private OnPressListener onPressListener;
    private OnReleaseListener onReleaseListener;

    //---- Construction ----
    public PushButtonC(final Button btn) {
        this.button = btn;
        this.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        onPressListener.onButtonPressed();
                        break;

                    case MotionEvent.ACTION_UP:
                        onReleaseListener.onButtonRelease();
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

    public void setOnReleaseListener(final OnReleaseListener listener) {
        this.onReleaseListener = listener;
    }

    public void setVisible(boolean visible) {
        this.button.setVisibility( visible ? View.VISIBLE : View.INVISIBLE);
    }

    public boolean isVisible() {
        return button.getVisibility() == Button.VISIBLE ? true : false;
    }
}
