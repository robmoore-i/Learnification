package com.rrm.learnification.settings.learnificationdelay;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.SettingsRepository;

public class SaveDelayFromPickerOnClickCommand implements OnClickCommand {
    private static final String LOG_TAG = "SaveDelayFromPickerOnClickCommand";

    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;
    private final DelayPicker delayPicker;

    public SaveDelayFromPickerOnClickCommand(AndroidLogger logger, SettingsRepository settingsRepository, DelayPicker delayPicker) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
        this.delayPicker = delayPicker;
    }

    @Override
    public void onClick() {
        int newDelayInSeconds = delayPicker.currentValueInSeconds();
        logger.i(LOG_TAG, "onValuePicked called with new value " + newDelayInSeconds + " (" + (newDelayInSeconds / 60) + " minutes)");
        settingsRepository.writeDelay(newDelayInSeconds);
    }
}
