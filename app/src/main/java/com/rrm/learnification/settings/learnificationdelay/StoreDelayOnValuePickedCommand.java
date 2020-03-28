package com.rrm.learnification.settings.learnificationdelay;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.SettingsRepository;

public class StoreDelayOnValuePickedCommand implements OnValuePickedCommand {
    private static final String LOG_TAG = "StoreDelayOnValuePickedCommand";
    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;

    public StoreDelayOnValuePickedCommand(AndroidLogger logger, SettingsRepository settingsRepository) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public void onValuePicked(int newDelayInSeconds) {
        logger.i(LOG_TAG, "onValuePicked called with new value " + newDelayInSeconds + " (" + (newDelayInSeconds / 60) + " minutes)");

        settingsRepository.writeDelay(newDelayInSeconds);
    }
}
