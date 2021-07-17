package es.sfernandez.raceyourtrack.app_error_handling;

public class AppCatchableException extends Exception {

    //---- Attributes ----
    private final AppError error;

    //---- Constructor ----
    public AppCatchableException(AppError error) {
        super();
        this.error = error;
    }

}
