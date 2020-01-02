package com.rrm.learnification.settings;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.logger.AndroidLogger;

class SaveDelayFromPickerOnClickCommand implements OnClickCommand {
    private static final String LOG_TAG = "SaveDelayFromPickerOnClickCommand";

    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;
    private final DelayPicker delayPicker;

    SaveDelayFromPickerOnClickCommand(AndroidLogger logger, SettingsRepository settingsRepository, DelayPicker delayPicker) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
        this.delayPicker = delayPicker;
    }

    @Override
    public void onClick() {
        int newDelayInSeconds = delayPicker.currentValueInSeconds();
        logger.v(LOG_TAG, "onValuePicked called with new value " + newDelayInSeconds + " (" + (newDelayInSeconds / 60) + " minutes)");
        settingsRepository.writeDelay(newDelayInSeconds);
    }
}
