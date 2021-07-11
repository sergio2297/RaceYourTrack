package es.sfernandez.raceyourtrack.app_error_handling;

public class AppException extends Exception {

    //---- Attributes ----
    private final AppError error;

    //---- Constructor ----
    public AppException(AppError error) {
        super();
        this.error = error;
    }

}
