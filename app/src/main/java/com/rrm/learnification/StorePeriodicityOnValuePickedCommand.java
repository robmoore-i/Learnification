package com.rrm.learnification;

class StorePeriodicityOnValuePickedCommand implements OnValuePickedCommand {
    private static final String LOG_TAG = "StorePeriodicityOnValuePickedCommand";
    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;

    StorePeriodicityOnValuePickedCommand(AndroidLogger logger, SettingsRepository settingsRepository) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public void onValuePicked(int newPeriodicityInSeconds) {
        logger.v(LOG_TAG, "onValuePicked called with new value " + newPeriodicityInSeconds + " (" + (newPeriodicityInSeconds / 60) + " minutes)");

        settingsRepository.writePeriodicity(newPeriodicityInSeconds);
    }
}
