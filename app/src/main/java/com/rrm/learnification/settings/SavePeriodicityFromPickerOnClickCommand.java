package com.rrm.learnification.settings;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.logger.AndroidLogger;

class SavePeriodicityFromPickerOnClickCommand implements OnClickCommand {
    private static final String LOG_TAG = "SavePeriodicityFromPickerOnClickCommand";

    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;
    private final PeriodicityPicker periodicityPicker;

    SavePeriodicityFromPickerOnClickCommand(AndroidLogger logger, SettingsRepository settingsRepository, PeriodicityPicker periodicityPicker) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
        this.periodicityPicker = periodicityPicker;
    }

    @Override
    public void onClick() {
        int newPeriodicityInSeconds = periodicityPicker.currentValueInSeconds();
        logger.v(LOG_TAG, "onValuePicked called with new value " + newPeriodicityInSeconds + " (" + (newPeriodicityInSeconds / 60) + " minutes)");
        settingsRepository.writePeriodicity(newPeriodicityInSeconds);
    }
}
