package es.sfernandez.raceyourtrack.app_error_handling;

import android.content.Intent;
import android.widget.Toast;

import es.sfernandez.raceyourtrack.R;

/**
 * Clase encargada de actuar frente a la creacion de errores dentro de la app. Las acciones pueden ir desde conducir
 * a una actividad concreta, mostrar un simple mensaje Toast o recuperar el sistema.
 *
 */
public class AppErrorHelper {

    //---- Definitions and Constants ----
    // Codigos: [0,4] --> Errores que no deben ocurrir bajo ningún concepto, y si ocurren es debido a un fallo del código fuente
    private static final int MIN_FATAL_ERROR_CODE = 0, MAX_FATAL_ERROR_CODE = 4;
    // Codigos: [5,12] --> Errores en los que intervienen las entradas de datos por parte del usuario
    private static final int MIN_INPUT_ERROR_CODE = 5, MAX_INPUT_ERROR_CODE = MIN_INPUT_ERROR_CODE + 7;

    private static final int MAX_ERROR_CODE = 200;

    //---- Handlers ----
    protected static void handleError(AppError error) {
        int errorCode = error.getCode();
        if(errorCode < MIN_FATAL_ERROR_CODE || errorCode > MAX_ERROR_CODE) { // El error ha sido creado con un codigo no valido
            new AppError(CodeErrors.MUST_NOT_HAPPEN_SRC_ERROR_CREATION, error.getContext().getResources().getString(R.string.src_error), error.getContext());
        } else if(MIN_FATAL_ERROR_CODE <= errorCode && errorCode <= MAX_FATAL_ERROR_CODE) { // Errores que nunca deberian de ocurrir
            Intent intent = new Intent(error.getContext(), FatalAppErrorActivity.class);
            intent.putExtra("ERROR_MSG",error.toString());
            error.getContext().startActivity(intent);
        } else if(MIN_INPUT_ERROR_CODE <= errorCode && errorCode <= MAX_INPUT_ERROR_CODE) { // Errores de entrada del usuario
            Toast.makeText(error.getContext(), error.getMsg(), Toast.LENGTH_LONG).show();
        }
    }

    //---- Errors codes ----
    public static class CodeErrors {
        public static final int MUST_NOT_HAPPEN = MIN_FATAL_ERROR_CODE + 0;      // Errores que no debería de ocurrir bajo ninguna circunstancia por la estructura del codigo fuente
        public static final int MUST_NOT_HAPPEN_SRC_ERROR_CREATION = MIN_FATAL_ERROR_CODE + 1;   // Error introducido en el codigo fuente durante la creacion de errores

        public static final int EMPTY_NAME_INPUT = MIN_INPUT_ERROR_CODE + 0; // Se ha dejado el nombre a introducir en blanco
        public static final int EMPTY_AMOUNT_INPUT = MIN_INPUT_ERROR_CODE + 1; // Se ha dejado la cantidad objetivo a introducir en blanco
        public static final int INVALID_AMOUNT_INPUT = MIN_INPUT_ERROR_CODE + 2; // La cantidad objetivo introducido es <= 0
        public static final int EMPTY_TARGET_AMOUNT_INPUT = MIN_INPUT_ERROR_CODE + 3; // Se ha dejado la cantidad objetivo a introducir en blanco
        public static final int EMPTY_CURRENT_AMOUNT_INPUT = MIN_INPUT_ERROR_CODE + 4; // Se ha dejado cantidad actual a introducir en blanco
        public static final int INVALID_TARGET_AMOUNT_INPUT = MIN_INPUT_ERROR_CODE + 5; // La cantidad objetivo introducido es <= 0
        public static final int INVALID_CURRENT_AMOUNT_INPUT = MIN_INPUT_ERROR_CODE + 6; // La cantidad objetivo introducida es <= 0 || > targetAmount
        public static final int NOT_A_NUMBER_TARGET_AMOUNT_INPUT = MIN_INPUT_ERROR_CODE + 7; // Se ha introducido un numero no valido (Decimales, no un numero...)

    }
}
