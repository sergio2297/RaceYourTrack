package es.sfernandez.raceyourtrack.app_error_handling;

import androidx.annotation.Nullable;

public class AppUnCatchableException extends RuntimeException {

    //---- Attributes ----
    private final AppError error;

    //---- Constructor ----
    public AppUnCatchableException(AppError error) {
        super();
        this.error = error;
    }

    @Nullable
    @Override
    public String getMessage() {
        return error.getMsg();
    }
}
