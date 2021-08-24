package es.sfernandez.raceyourtrack.carController;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import es.sfernandez.raceyourtrack.R;
import model.Game;
import utils.ObjectProperty;

public class CarInfoFragment extends Fragment {

    //---- Attributes ----
    private ObjectProperty<Integer> currentGear = new ObjectProperty<>(0);

    //---- View Elements ----
    private TextView txtCurrentGearDisplay;

    //---- Constructor ----
    public CarInfoFragment() {
        currentGear.addListener( (oldValue, newValue) -> {
            switchDisplayToGear((Integer)newValue);
        });
    }

    //---- Fragment Methods ----
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_controller_car_info, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewElements();
        currentGear.setProperty(0);
    }

    //---- Methods ----
    private void initializeViewElements() {
        this.txtCurrentGearDisplay = getView().findViewById(R.id.txt_current_gear_display);
    }

    public Integer getCurrentGear() {
        return currentGear.getProperty();
    }

    public void downShift() {
        shiftTo(currentGear.getProperty() - 1);
    }

    public void upShift() {
        shiftTo(currentGear.getProperty() + 1);
    }

    public void shiftTo(final Integer gear) {
        if(-1 <= gear && gear <= Game.getInstance().getSelectedCar().getNumOfGears()) {
            currentGear.setProperty(gear);
        }
    }

    private void switchDisplayToGear(final Integer gear) {
        switch(gear.intValue()) {
            case -1:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_r);
                break;

            case 0:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_n);
                break;

            case 1:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_1);
                break;

            case 2:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_2);
                break;

            case 3:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_3);
                break;

            case 4:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_4);
                break;

            case 5:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_5);
                break;

            case 6:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_6);
                break;

            case 7:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_7);
                break;

            case 8:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_8);
                break;

            case 9:
                txtCurrentGearDisplay.setBackgroundResource(R.drawable.png_7_segment_display_num_9);
                break;

            default:
                break;
        }
    }
}
