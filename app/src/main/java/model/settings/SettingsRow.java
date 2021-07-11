package model.settings;

public class SettingsRow {

    //---- Attributes ----
    private final String group, variable;
    private String value;

    //---- Construction ----
    public SettingsRow(final String group, final String variable) {
        this(group, variable, "");
    }

    public SettingsRow(final String group, final String variable, final String value) {
        this.group = group;
        this.variable = variable;
        this.value = value;
    }

    //---- Methods ----
    public String getGroup() {
        return group;
    }

    public String getVariable() {
        return variable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
