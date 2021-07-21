package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class offer the user the possibility to add value change listeners to his properties
 */
public class ObjectProperty<T> {

    //---- Constants and Definitions ----
    public interface ValueChangeListener<T> {
        public void onValueChange(final T oldValue, final T newValue);
    }

    //---- Attributes ----
    private T property;
    private List<ValueChangeListener> listListeners;

    //---- Constructor ----
    public ObjectProperty(final T property) {
        this.property = property;
        listListeners = new ArrayList<ValueChangeListener>();
    }

    //---- Methods ----
    public T getProperty() {
        return this.property;
    }

    public void setProperty(T property) {
        T oldValue = this.property;
        this.property = property;
        for(ValueChangeListener listener : listListeners) {
            listener.onValueChange(oldValue, property);
        }
    }

    public void addListener(final ValueChangeListener valueChangeListener) {
        listListeners.add(valueChangeListener);
    }
}
