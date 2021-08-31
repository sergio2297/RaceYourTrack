package es.sfernandez.raceyourtrack;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class RaceYourTrackApplication extends Application {

    //---- Attributes ----
    private static WeakReference<Context> context; // Made with WeakReference to avoid memory leaking
    private static int screenWidthPx, screenHeightPx;

    //---- Application Methods ----
    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(this);

        getScreenResolution();
    }

    public static Context getContext(){
        return context.get();
    }

    private void getScreenResolution() {
        WindowManager wm = (WindowManager) context.get().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidthPx = metrics.widthPixels;
        screenHeightPx = metrics.heightPixels;
    }

    public static int getScreenWidthPx() {
        return screenWidthPx;
    }

    public static int getScreenHeightPx() {
        return screenHeightPx;
    }
}
