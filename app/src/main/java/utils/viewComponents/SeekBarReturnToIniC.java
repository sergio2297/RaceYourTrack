package utils.viewComponents;

import android.widget.SeekBar;

/**
 * This custom component acts like a seekbar that returns to its initial position smoothie when the user stop touch it.
 */
public abstract class SeekBarReturnToIniC {

    //---- Constants and Definitions ----
    public interface OnProgressChangeListener {
        void onProgressChange(final int progress);
    }

    private class DecreaseProgressThread extends Thread {

        //---- Constants and Definitions ----
        private final int PERIOD_MS = 50;

        //---- Attributes ----
        private final SeekBar seekBar;
        private final int targetProgress, increment;

        //---- Constructor ----
        protected DecreaseProgressThread(final SeekBar seekBar, final int targetProgress, final int increment) {
            this.seekBar = seekBar;
            this.targetProgress = targetProgress;
            this.increment = increment;
        }

        //---- Methods ----
        @Override
        public void run() {
            try {
                if (seekBar.getProgress() < targetProgress) {
                    while (seekBar.getProgress() < targetProgress) {
                        seekBar.setProgress(seekBar.getProgress() + increment);
                        Thread.sleep(PERIOD_MS);
                    }
                } else {
                    while (seekBar.getProgress() > targetProgress) {
                        seekBar.setProgress(seekBar.getProgress() - increment);
                        Thread.sleep(PERIOD_MS);
                    }
                }
                seekBar.setProgress(targetProgress);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //---- Attributes ----
    protected final SeekBar seekBar;
    protected OnProgressChangeListener listener;
    protected final int startProgress, decreaseRate;
    private DecreaseProgressThread decreaseProgressThread = null;

    //---- Construction ----
    public SeekBarReturnToIniC(final SeekBar seekBar, final int startProgress, final int decreaseRate) {
        this.seekBar = seekBar;
        this.startProgress = startProgress;
        this.decreaseRate = decreaseRate;

        initializeListeners();
    }

    //---- Methods ----
    private void initializeListeners() {
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                listener.onProgressChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(decreaseProgressThread != null) {
                    decreaseProgressThread.interrupt();
                    decreaseProgressThread = null;
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {
                decreaseProgressThread = new DecreaseProgressThread(seekBar, startProgress, decreaseRate);
                decreaseProgressThread.start();
            }
        });
    }

    public void setListener(final OnProgressChangeListener listener) {
        this.listener = listener;
    }

    public void setVisible(final boolean visible) {
        this.seekBar.setVisibility(visible ? SeekBar.VISIBLE : SeekBar.INVISIBLE);
    }

    public boolean isVisible() {
        return seekBar.getVisibility() == SeekBar.VISIBLE ? true : false;
    }

    public int getProgress() {
        return seekBar.getProgress();
    }

    public void setProgress(final int progress) {
        this.seekBar.setProgress(progress);
    }
}
