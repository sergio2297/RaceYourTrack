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
import es.sfernandez.raceyourtrack.app_error_handling.AppErrorHelper;
import es.sfernandez.raceyourtrack.app_error_handling.AppUnCatchableException;
import model.Car;
import model.Game;
import model.carController.CarController;
import model.carController.TransmissionSystemController;
import model.settings.Settings;
import model.settings.configurable.PedalsConfig;
import model.settings.configurable.TransmissionConfig;
import utils.viewComponents.PushButtonC;

public class TransmissionFragment extends Fragment {

    //---- Attributes ----
    private TransmissionConfig transmissionConfig;
    private CarController carController;
    private TransmissionSystemController transmissionSystemController;

    //---- View Elements ----
    private PushButtonC btnDownShift, btnUpShift, btnNeutral, btnReverse;

    //---- Constructor ----
    public TransmissionFragment() {
        transmissionSystemController = new TransmissionSystemController();
    }

    //---- Fragment Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transmissionConfig = Settings.getInstance().getTransmissionConfig();
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
        btnDownShift = new PushButtonC((Button) getView().findViewById(R.id.btn_down_shift));
        btnUpShift = new PushButtonC((Button) getView().findViewById(R.id.btn_up_shift));
        btnNeutral = new PushButtonC((Button) getView().findViewById(R.id.btn_neutral));
        btnReverse = new PushButtonC((Button) getView().findViewById(R.id.btn_reverse));

        if(Settings.getInstance().getPedalsConfig().equals(PedalsConfig.ARROWS)) {
            btnNeutral.setVisible(false);
            btnReverse.setVisible(false);
        }
        if(transmissionConfig.equals(TransmissionConfig.AUTOMATIC)) {
            btnDownShift.setVisible(false);
            btnUpShift.setVisible(false);
        } else if(transmissionConfig.equals(TransmissionConfig.MANUAL)) {
            Car selectedCar = Game.getInstance().getSelectedCar();
            if(selectedCar.getTransmissionConfig().equals(TransmissionConfig.H_SHIFT)) {
                btnDownShift.setVisible(false);
                btnUpShift.setVisible(false);
            } else {
                btnDownShift.setVisible(true);
                btnUpShift.setVisible(true);
            }
        } else {
            throw new AppUnCatchableException(new AppError(AppErrorHelper.CodeErrors.MUST_NOT_HAPPEN, RaceYourTrackApplication.getContext().getResources().getString(R.string.src_error), RaceYourTrackApplication.getContext()));
        }
    }

    private void addListenersToViewElements() {
        if(btnDownShift.isVisible()) {
            btnDownShift.setOnPressListener(() -> carController.addTransmissionAction(transmissionSystemController.buildActionShiftDown()));
            btnDownShift.setOnReleaseListener(() -> {});
        }

        if(btnUpShift.isVisible()) {
            btnUpShift.setOnPressListener(() -> carController.addTransmissionAction(transmissionSystemController.buildActionShiftUp()));
            btnUpShift.setOnReleaseListener(() -> {});
        }

        if(btnNeutral.isVisible()) {
            btnNeutral.setOnPressListener(() -> carController.addTransmissionAction(transmissionSystemController.buildActionNeutral()));
            btnNeutral.setOnReleaseListener(() -> {});
        }

        if(btnReverse.isVisible()) {
            btnReverse.setOnPressListener(() -> carController.addTransmissionAction(transmissionSystemController.buildActionReverse()));
            btnReverse.setOnReleaseListener(() -> {});
        }
    }

    public void setCarController(final CarController carController) {
        this.carController = carController;
    }
}
