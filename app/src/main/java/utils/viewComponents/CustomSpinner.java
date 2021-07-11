package utils.viewComponents;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;

public class CustomSpinner<T> {

    //---- Attributes ----
    private boolean selectionIsUserGenerated;
    private final AppCompatSpinner spinner;
    private OnUserSelectItemListener listener;

    //---- Construction ----
    public CustomSpinner(final AppCompatSpinner spinner) {
        this.spinner = spinner;
        this.selectionIsUserGenerated = true;

        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(selectionIsUserGenerated) {
                    listener.onSelection();
                }
                selectionIsUserGenerated = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    //---- Methods ----
    public void setSelectableValues(final T[] values) {
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(spinner.getContext(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * @return the position of obj in the adapter or -1 if it is not found.
     * @throws RuntimeException if the component hasn't got an adapter attached.
     */
    public int getPositionOf(final T obj) {
        if(spinner.getAdapter() == null) {
            throw new RuntimeException();
        }

        int i = 0;
        while(i < spinner.getAdapter().getCount()) {
            if(spinner.getAdapter().getItem(i).equals(obj)) {
                return i;
            } else {
                ++i;
            }
        }
        return -1;
    }

    public T getSelectedItem() {
        return (T)spinner.getSelectedItem();
    }

    public void setSelection(final T obj, final boolean isUserGenerated) {
        selectionIsUserGenerated = isUserGenerated;
        spinner.setSelection(getPositionOf(obj)); // Only call the listener iff the selected value has changed
    }

    public void setOnUserSelectItemListener(OnUserSelectItemListener listener) {
        this.listener = listener;
    }

    //---- OnUserSelectItemListener ----
    public interface OnUserSelectItemListener {
        public void onSelection();
    }
}
