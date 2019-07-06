package com.rrm.learnification;

class StorePeriodicityOnChangeCommand implements OnChangeCommand {
    private static final String LOG_TAG = "StorePeriodicityOnChangeCommand";
    private final AndroidLogger logger;

    StorePeriodicityOnChangeCommand(AndroidLogger logger) {
        this.logger = logger;
    }

    @Override
    public void onChange(int newValue) {
        logger.v(LOG_TAG, "onChange called with new value " + newValue);
    }
}
