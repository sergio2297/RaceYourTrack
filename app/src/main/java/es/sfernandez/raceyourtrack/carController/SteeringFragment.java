package es.sfernandez.raceyourtrack.carController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;
import model.carController.CarController;
import model.carController.SteeringSystemController;
import model.settings.Settings;
import model.settings.configurable.SteeringConfig;
import utils.viewComponents.KeepTouchedButtonC;
import utils.viewComponents.SeekBarSteeringWheelC;

public class SteeringFragment extends Fragment {

    //---- Constants and Definitions ----
    private final int MINIMUM_MS_DELAY_BETWEEN_COMMANDS = 50;

    //---- Attributes ----
    private SteeringConfig steeringConfig;
    private CarController carController;
    private SteeringSystemController steeringSystemController;
    private long lastSteeringCommandTimestamp = -MINIMUM_MS_DELAY_BETWEEN_COMMANDS;

    //---- View Elements ----
    private KeepTouchedButtonC btnArrowLeft, btnArrowRight;
    private SeekBarSteeringWheelC seekBarSteeringWheel;

    //---- Constructor ----
    public SteeringFragment() {
        steeringSystemController = new SteeringSystemController();
    }

    //---- Fragment Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        steeringConfig = Settings.getInstance().getSteeringConfig();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_controller_steering, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewElements();
        addListenersToViewElements();
    }

    //---- Methods ----
    private void initializeViewElements() {
        btnArrowLeft = new KeepTouchedButtonC((Button) getView().findViewById(R.id.btn_left_arrow));
        btnArrowRight = new KeepTouchedButtonC((Button) getView().findViewById(R.id.btn_right_arrow));
        seekBarSteeringWheel = new SeekBarSteeringWheelC(getView().findViewById(R.id.seek_bar_steering_wheel));

        if(steeringConfig.equals(SteeringConfig.ARROWS)) {
            seekBarSteeringWheel.setVisible(false);
        } else if(steeringConfig.equals(SteeringConfig.STEERING_WHEEL)) {
            btnArrowLeft.setVisible(false);
            btnArrowRight.setVisible(false);
        } else {
            throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
    }

    // TODO: Esto presenta errores cuando se pulsen botones a la vez. Esto se debe controlar. Lo mismo ocurre con otros controles
    private void addListenersToViewElements() {
        if(btnArrowLeft.isVisible()) {
            btnArrowLeft.setOnPressListener(() -> carController.addSteeringAction(steeringSystemController.buildActionSteeringLeft()));
            btnArrowLeft.setOnReleaseListener(() -> carController.addSteeringAction(steeringSystemController.buildActionCenterSteering()));
        }

        if(btnArrowRight.isVisible()) {
            btnArrowRight.setOnPressListener(() -> carController.addSteeringAction(steeringSystemController.buildActionSteeringRight()));
            btnArrowRight.setOnReleaseListener(() -> carController.addSteeringAction(steeringSystemController.buildActionCenterSteering()));
        }

        // The steeringWheel will send commands with some delay restrictions to avoid collapse the communication queue
        if(seekBarSteeringWheel.isVisible()) {
            seekBarSteeringWheel.setListener((progress) -> {
                if(progress == 90) {
                    carController.addSteeringAction(steeringSystemController.buildActionCenterSteering());
                } else if(System.currentTimeMillis() - lastSteeringCommandTimestamp > MINIMUM_MS_DELAY_BETWEEN_COMMANDS) {
                    int degrees = Math.abs(90 - progress);
                    carController.addSteeringAction((progress - 90) <= 0 ?
                            steeringSystemController.buildActionSteeringLeft(degrees) :
                            steeringSystemController.buildActionSteeringRight(degrees));
                    lastSteeringCommandTimestamp = System.currentTimeMillis();
                }
            });
        }
    }

    public void setCarController(final CarController carController) {
        this.carController = carController;
    }
}
