package es.sfernandez.raceyourtrack.carController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;

import es.sfernandez.raceyourtrack.R;
import es.sfernandez.raceyourtrack.RaceYourTrackApplication;
import es.sfernandez.raceyourtrack.app_error_handling.AppError;
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHandler;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;
import model.carController.CarController;
import model.carController.LightsSystemController;
import model.carController.TransmissionSystemController;
import model.settings.Settings;
import model.settings.configurable.PedalsConfig;
import utils.viewComponents.KeepTouchedButtonC;
import utils.viewComponents.SeekBarAdvancedThrottleC;

public class PedalsFragment extends Fragment {

    //---- Constants and Definitions ----
    private final int MINIMUM_MS_DELAY_BETWEEN_COMMANDS = 100;

    //---- Attributes ----
    private PedalsConfig pedalsConfig;
    private CarController carController;
    private final LightsSystemController lightsSystemController;
    private final TransmissionSystemController transmissionSystemController;
    private boolean isCarBraking = false, isCarAccelerating = false;
    private String lastCommandSent = "";
    private long lastAdvancedThrottleCommandTimestamp = -MINIMUM_MS_DELAY_BETWEEN_COMMANDS;

    //---- View Elements ----
    private KeepTouchedButtonC btnArrowUp, btnArrowDown;
    private KeepTouchedButtonC btnThrottle, btnBrake;
    private SeekBarAdvancedThrottleC seekBarAdvancedThrottle;

    //---- Constructor ----
    public PedalsFragment() {
        lightsSystemController = new LightsSystemController();
        transmissionSystemController = new TransmissionSystemController();
    }

    //---- Fragment Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pedalsConfig = Settings.getInstance().getPedalsConfig();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_controller_pedals, container, false);
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
        btnArrowUp = new KeepTouchedButtonC((Button) getView().findViewById(R.id.btn_up_arrow));
        btnArrowDown = new KeepTouchedButtonC((Button) getView().findViewById(R.id.btn_down_arrow));
        btnThrottle = new KeepTouchedButtonC((Button) getView().findViewById(R.id.btn_throttle));
        btnBrake = new KeepTouchedButtonC((Button) getView().findViewById(R.id.btn_brake));
        seekBarAdvancedThrottle = new SeekBarAdvancedThrottleC((SeekBar) getView().findViewById(R.id.seek_bar_advanced_pedal));

        if(pedalsConfig.equals(PedalsConfig.ARROWS)) {
            btnThrottle.setVisible(false);
            btnBrake.setVisible(false);
            seekBarAdvancedThrottle.setVisible(false);
        } else if(pedalsConfig.equals(PedalsConfig.BOTH)) {
            btnArrowUp.setVisible(false);
            btnArrowDown.setVisible(false);
            seekBarAdvancedThrottle.setVisible(false);
        } else if(pedalsConfig.equals(PedalsConfig.ADVANCED)) {
            btnArrowUp.setVisible(false);
            btnArrowDown.setVisible(false);
            btnThrottle.setVisible(false);
            btnBrake.setVisible(false);
        } else {
            throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
    }

    private void addListenersToViewElements() {
        if(btnArrowUp.isVisible()) {
            btnArrowUp.setOnPressListener(() -> {
                carController.addTransmissionAction(transmissionSystemController.buildActionShiftTo(1));
                carController.addTransmissionAction(transmissionSystemController.buildActionThrottle());
            });
            btnArrowUp.setOnReleaseListener(() -> carController.addTransmissionAction(transmissionSystemController.buildActionStopThrottle()));
        }

        if(btnArrowDown.isVisible()) {
            btnArrowDown.setOnPressListener(() -> {
                carController.addTransmissionAction(transmissionSystemController.buildActionReverse());
                carController.addLightsAction(lightsSystemController.buildActionTurnOnReverseLights());
                carController.addTransmissionAction(transmissionSystemController.buildActionThrottle());
            });
            btnArrowDown.setOnReleaseListener(() -> {
                carController.addTransmissionAction(transmissionSystemController.buildActionStopThrottle());
                carController.addLightsAction(lightsSystemController.buildActionTurnOffReverseLights());
            });
        }

        if(btnThrottle.isVisible()) {
            btnThrottle.setOnPressListener(() -> {
                if (!isCarBraking) {
                    carController.addTransmissionAction(transmissionSystemController.buildActionThrottle());
                }
            });
            btnThrottle.setOnReleaseListener(() -> carController.addTransmissionAction(transmissionSystemController.buildActionStopThrottle()));
        }

        if(btnBrake.isVisible()) {
            btnBrake.setOnPressListener(() -> {
                isCarBraking = true;
                carController.addTransmissionAction(transmissionSystemController.buildActionBrake());
                carController.addLightsAction(lightsSystemController.buildActionTurnOnBrakeLights());
            });
            btnBrake.setOnReleaseListener(() -> {
                carController.addLightsAction(lightsSystemController.buildActionTurnOffBrakeLights());
                isCarBraking = false;
            });
        }

        // The advanced throttle will send commands with some delay restrictions to avoid collapse the communication queue
        if(seekBarAdvancedThrottle.isVisible()) {
            seekBarAdvancedThrottle.setListener((progress) -> {
                String command = lastCommandSent;
                if(progress == 0) {
                    command = transmissionSystemController.buildActionStopThrottle();
                    isCarAccelerating = false;
                } else if(!isCarAccelerating) {
                    carController.addTransmissionAction(transmissionSystemController.buildActionThrottle());
                    command = transmissionSystemController.buildActionShiftTo((int) (progress / transmissionSystemController.MAX_NUM_OF_GEARS));
                    isCarAccelerating = true;
                } else if(progress == 89) { // Max progress of the seekBar (see layout)
                    command = transmissionSystemController.buildActionShiftTo(transmissionSystemController.MAX_NUM_OF_GEARS);
                } else if(System.currentTimeMillis() - lastAdvancedThrottleCommandTimestamp > MINIMUM_MS_DELAY_BETWEEN_COMMANDS) {
                    command = transmissionSystemController.buildActionShiftTo((int)(progress / transmissionSystemController.MAX_NUM_OF_GEARS));
                    lastAdvancedThrottleCommandTimestamp = System.currentTimeMillis();
                }

                if(!command.equals(lastCommandSent)) {
                    carController.addTransmissionAction(command);
                    lastCommandSent = command;
                }
            });
        }
    }

    public void setCarController(final CarController carController) {
        this.carController = carController;

        if(seekBarAdvancedThrottle.isVisible()) {
            // The advanced throttle will not send the specified power. It will control the power of the car using gears. So it's necessary to reconfigure the
            // car with the maximum number of Gears. Depends on the progress, the car will shift to one or another gear.
            carController.addTransmissionAction(transmissionSystemController.buildActionConfigHShift());
            carController.addTransmissionAction(transmissionSystemController.buildActionShiftTo(9));
        }
    }
}
