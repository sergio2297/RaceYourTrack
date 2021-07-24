package es.sfernandez.raceyourtrack.app_error_handling;

import android.content.Context;

import es.sfernandez.raceyourtrack.R;

/**
 * //TODO:
 */
public class AppError {

    //---- Attributes ----
    private final int code;
    private final String msg;
    private final Context context;

    //---- Constructor ----
    public AppError(final int code, final String msg, final Context context) {
        this.code = code;
        this.msg = msg;
        this.context = context;

        AppErrorHandler.handleError(this);
    }

    //---- Methods ----
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public String toString() {
        return context.getResources().getString(R.string.code) + ": " + code + ". " + msg;
    }
}
