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
    public void onChange(int newValue) {
        logger.v(LOG_TAG, "onChange called with new value " + newValue);

        settingsRepository.writePeriodicity(newValue);
    }
}
