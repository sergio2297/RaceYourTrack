package es.sfernandez.raceyourtrack;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

public class RaceYourTrackApplication extends Application {

    //---- Attributes ----
    private static WeakReference<Context> context; // Made with WeakReference to avoid memory leaking

    //---- Application Methods ----
    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(this);
    }

    public static Context getContext(){
        return context.get();
    }
}
