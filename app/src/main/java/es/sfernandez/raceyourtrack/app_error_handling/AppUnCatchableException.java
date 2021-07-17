package es.sfernandez.raceyourtrack.app_error_handling;

public class AppUnCatchableException extends RuntimeException {

    //---- Attributes ----
    private final AppError error;

    //---- Constructor ----
    public AppUnCatchableException(AppError error) {
        super();
        this.error = error;
    }

}
