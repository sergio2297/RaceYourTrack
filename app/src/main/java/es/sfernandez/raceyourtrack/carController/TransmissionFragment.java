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
import model.Car;
import model.Game;
import model.carController.CarController;
import model.carController.LightsSystemController;
import model.carController.TransmissionSystemController;
import model.settings.Settings;
import model.settings.configurable.PedalsConfig;
import model.settings.configurable.TransmissionConfig;
import utils.viewComponents.GearButtonC;

public class TransmissionFragment extends Fragment {

    //---- Attributes ----
    private int currentGear = 0;
    private TransmissionConfig transmissionConfig;
    private CarController carController;
    private CarInfoFragment carInfoFragment;
    private TransmissionSystemController transmissionSystemController;
    private LightsSystemController lightsSystemController;

    //---- View Elements ----
    private GearButtonC btnDownShift, btnUpShift, btnForward, btnNeutral, btnReverse;

    //---- Constructor ----
    public TransmissionFragment() {
        transmissionSystemController = new TransmissionSystemController();
        lightsSystemController = new LightsSystemController();

        transmissionConfig = Settings.getInstance().getTransmissionConfig();
    }

    //---- Fragment Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_controller_transmission, container, false);
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
        btnDownShift = new GearButtonC((Button) getView().findViewById(R.id.btn_down_shift));
        btnUpShift = new GearButtonC((Button) getView().findViewById(R.id.btn_up_shift));
        btnForward = new GearButtonC((Button) getView().findViewById(R.id.btn_forward));

        View lytAutomaticShiftWithPedals = getView().findViewById(R.id.lyt_automatic_shift_with_pedals);
        View lytManualSequentialShift = getView().findViewById(R.id.lyt_manual_sequential_shift);
        // TODO: H Shift

        if(Settings.getInstance().getPedalsConfig().equals(PedalsConfig.ARROWS)) {
            lytAutomaticShiftWithPedals.setVisibility(View.GONE);
            lytManualSequentialShift.setVisibility(View.GONE);
        } else {
            if(transmissionConfig.equals(TransmissionConfig.AUTOMATIC)) {
                lytManualSequentialShift.setVisibility(View.GONE);
                btnNeutral = new GearButtonC((Button) getView().findViewById(R.id.btn_neutral_automatic_shift));
                btnReverse = new GearButtonC((Button) getView().findViewById(R.id.btn_reverse_automatic_shift));
            } else if(transmissionConfig.equals(TransmissionConfig.MANUAL)) {
                lytAutomaticShiftWithPedals.setVisibility(View.GONE);
                Car selectedCar = Game.getInstance().getSelectedCar();
                if(selectedCar.getTransmissionConfig().equals(TransmissionConfig.H_SHIFT)) {
                    lytManualSequentialShift.setVisibility(View.GONE);
                    //TODO:
                } else {
                    // TODO:
                    btnNeutral = new GearButtonC((Button) getView().findViewById(R.id.btn_neutral_sequential_shift));
                    btnReverse = new GearButtonC((Button) getView().findViewById(R.id.btn_reverse_sequential_shift));
                }
            } else {
                throw new AppUnCatchableException(new AppError(AppErrorHandler.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
            }
        }
    }

    private void addListenersToViewElements() {
        if(btnDownShift.isVisible()) {
            btnDownShift.setOnPressListener(() -> {
                carController.addTransmissionAction(transmissionSystemController.buildActionShiftDown());
                carInfoFragment.downShift();
                --currentGear;
                if(currentGear == -1) {
                    carController.addLightsAction(lightsSystemController.buildActionTurnOnReverseLights());
                }
            });
        }

        if(btnUpShift.isVisible()) {
            btnUpShift.setOnPressListener(() -> {
                carController.addTransmissionAction(transmissionSystemController.buildActionShiftUp());
                carInfoFragment.upShift();
                ++currentGear;
                if(currentGear == 0) {
                    carController.addLightsAction(lightsSystemController.buildActionTurnOffReverseLights());
                }
            });
        }

        if(btnForward.isVisible()) {
            btnForward.setOnPressListener(() -> {
                carController.addTransmissionAction(transmissionSystemController.buildActionShiftTo(1));
                carController.addLightsAction(lightsSystemController.buildActionTurnOffReverseLights());
                carInfoFragment.shiftTo(CarInfoFragment.FORWARD_GEAR_NUMBER);
            });
        }

        if(btnNeutral != null && btnNeutral.isVisible()) {
            btnNeutral.setOnPressListener(() -> {
                carController.addTransmissionAction(transmissionSystemController.buildActionNeutral());
                carController.addLightsAction(lightsSystemController.buildActionTurnOffReverseLights());
                carInfoFragment.shiftTo(0);
                currentGear = 0;
            });
        }

        if(btnReverse != null && btnReverse.isVisible()) {
            btnReverse.setOnPressListener(() -> {
                carController.addTransmissionAction(transmissionSystemController.buildActionReverse());
                carController.addLightsAction(lightsSystemController.buildActionTurnOnReverseLights());
                carInfoFragment.shiftTo(-1);
                currentGear = -1;
            });
        }
    }

    public void setCarController(final CarController carController) {
        this.carController = carController;
    }

    public void setCarInfoFragment(final CarInfoFragment carInfoFragment) {
        this.carInfoFragment = carInfoFragment;
    }
}
