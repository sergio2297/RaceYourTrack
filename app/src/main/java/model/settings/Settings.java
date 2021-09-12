package model.settings;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.dao.DaoSettings;
import model.settings.configurable.DrivingModeConfig;
import model.settings.configurable.PedalsConfig;
import model.settings.configurable.SteeringConfig;
import model.settings.configurable.TransmissionConfig;
import utils.Utils;

public class Settings {

    //---- Constants and Definitions ----
    private static Settings settings = null;

    //---- Attributes ----
    private boolean soundsOn;
    private DrivingModeConfig drivingModeConfig;
    private PedalsConfig pedalsConfig;
    private TransmissionConfig transmissionConfig;
    private SteeringConfig steeringConfig;

    //---- Construction ----
    private Settings() {
        loadSettingsFromDB();
    }

    public static Settings getInstance() {
        if(settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    //---- Methods ----
    public static void initializeSettingsDB() {
        List<SettingsRow> listSettingsRows = new ArrayList<SettingsRow>();
        listSettingsRows.add(new SettingsRow(DaoSettings.SOUND_GROUP, DaoSettings.ENABLE_VAR, DaoSettings.TRUE_VALUE));
        listSettingsRows.add(new SettingsRow(DaoSettings.CONTROL_GROUP, DaoSettings.CONTROL_CONFIG_TYPE_VAR, DrivingModeConfig.BASIC.getName()));
        listSettingsRows.add(new SettingsRow(DaoSettings.CONTROL_GROUP, DaoSettings.PEDALS_VAR, DrivingModeConfig.BASIC.getPedalsConfig().getName()));
        listSettingsRows.add(new SettingsRow(DaoSettings.CONTROL_GROUP, DaoSettings.STEERING_VAR, DrivingModeConfig.BASIC.getSteeringConfig().getName()));
        listSettingsRows.add(new SettingsRow(DaoSettings.CONTROL_GROUP, DaoSettings.TRANSMISSION_VAR, DrivingModeConfig.BASIC.getTransmissionConfig().getName()));
        listSettingsRows.add(new SettingsRow(DaoSettings.GENERAL_GROUP, DaoSettings.CURRENT_COINS_VAR, "0"));

        DaoSettings daoSettings = new DaoSettings();
        for(SettingsRow row : listSettingsRows) {
            daoSettings.set(row);
        }
    }

    private void loadSettingsFromDB() {
        DaoSettings daoSettings = new DaoSettings();

        soundsOn = Utils.parseToBoolean(daoSettings.get(DaoSettings.SOUND_GROUP, DaoSettings.ENABLE_VAR).getValue());
        drivingModeConfig = DrivingModeConfig.valueOf(daoSettings.get(DaoSettings.CONTROL_GROUP, DaoSettings.CONTROL_CONFIG_TYPE_VAR).getValue());
        pedalsConfig = PedalsConfig.valueOf(daoSettings.get(DaoSettings.CONTROL_GROUP, DaoSettings.PEDALS_VAR).getValue());
        steeringConfig = SteeringConfig.valueOf(daoSettings.get(DaoSettings.CONTROL_GROUP, DaoSettings.STEERING_VAR).getValue());
        transmissionConfig = TransmissionConfig.valueOf(daoSettings.get(DaoSettings.CONTROL_GROUP, DaoSettings.TRANSMISSION_VAR).getValue());
        Log.println(Log.INFO, Settings.class.getCanonicalName(), "Settings have been correctly loaded from database.");
    }

    public void saveSettings() {
        List<SettingsRow> listSettingsRows = new ArrayList<SettingsRow>();
        listSettingsRows.add(new SettingsRow(DaoSettings.SOUND_GROUP, DaoSettings.ENABLE_VAR, soundsOn ? DaoSettings.TRUE_VALUE : DaoSettings.FALSE_VALUE));
        listSettingsRows.add(new SettingsRow(DaoSettings.CONTROL_GROUP, DaoSettings.CONTROL_CONFIG_TYPE_VAR, drivingModeConfig.getName()));
        listSettingsRows.add(new SettingsRow(DaoSettings.CONTROL_GROUP, DaoSettings.PEDALS_VAR, pedalsConfig.getName()));
        listSettingsRows.add(new SettingsRow(DaoSettings.CONTROL_GROUP, DaoSettings.STEERING_VAR, steeringConfig.getName()));
        listSettingsRows.add(new SettingsRow(DaoSettings.CONTROL_GROUP, DaoSettings.TRANSMISSION_VAR, transmissionConfig.getName()));

        Log.println(Log.INFO, Settings.class.getCanonicalName(), "Storing settings in database.");
        DaoSettings daoSettings = new DaoSettings();
        for(SettingsRow row : listSettingsRows) {
            daoSettings.set(row);
        }
        Log.println(Log.INFO, Settings.class.getCanonicalName(), "Settings have been correctly stored in database.");
    }

    public boolean isSoundsOn() {
        return soundsOn;
    }

    public void setSoundsOn(boolean soundsOn) {
        this.soundsOn = soundsOn;
    }

    public DrivingModeConfig getDrivingModeConfig() {
        return drivingModeConfig;
    }

    public void setDrivingModeConfig(final DrivingModeConfig drivingModeConfig) {
        this.drivingModeConfig = drivingModeConfig;

        this.pedalsConfig = drivingModeConfig.getPedalsConfig();
        this.steeringConfig = drivingModeConfig.getSteeringConfig();
        this.transmissionConfig = drivingModeConfig.getTransmissionConfig();
    }

    public PedalsConfig getPedalsConfig() {
        return pedalsConfig;
    }

    public void setPedalsConfig(final PedalsConfig pedalsConfig) {
        this.pedalsConfig = pedalsConfig;
    }

    public TransmissionConfig getTransmissionConfig() {
        return transmissionConfig;
    }

    public void setTransmissionConfig(final TransmissionConfig transmissionConfig) {
        this.transmissionConfig = transmissionConfig;
    }

    public SteeringConfig getSteeringConfig() {
        return steeringConfig;
    }

    public void setSteeringConfig(final SteeringConfig steeringConfig) {
        this.steeringConfig = steeringConfig;
    }
}
