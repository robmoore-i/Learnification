package com.rrm.learnification;

class StorePeriodicityOnValuePickedCommand implements OnValuePickedCommand {
    private static final String LOG_TAG = "StorePeriodicityOnValuePickedCommand";
    private final AndroidLogger logger;

    StorePeriodicityOnValuePickedCommand(AndroidLogger logger) {
        this.logger = logger;
    }

    @Override
    public void onChange(int newValue) {
        logger.v(LOG_TAG, "onChange called with new value " + newValue);
    }
}
