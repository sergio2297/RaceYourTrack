package model.settings.configurable;

import java.io.Serializable;

public abstract class ConfigurableProperty implements Serializable {

    //---- Attributes ----
    private final char abbreviation;
    private final String name;

    //---- Constructor ----
    protected ConfigurableProperty(final char abbreviation, final String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    //---- Methods ----
    public char getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof ConfigurableProperty) {
            ConfigurableProperty configurableProperty = (ConfigurableProperty)obj;
            return this.getClass().equals(obj.getClass()) &&
                    this.abbreviation == configurableProperty.getAbbreviation() &&
                    this.name.equals(configurableProperty.getName());
        }
        return false;
    }

}
