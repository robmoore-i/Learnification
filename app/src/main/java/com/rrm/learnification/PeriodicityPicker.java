package com.rrm.learnification;

import android.widget.NumberPicker;

class PeriodicityPicker {
    private static final String LOG_TAG = "PeriodicityPicker";

    private final AndroidLogger logger;
    private final NumberPicker periodicityPicker;

    PeriodicityPicker(AndroidLogger logger, MainActivityView mainActivityView) {
        this.logger = logger;
        this.periodicityPicker = mainActivityView.getPeriodicityPicker();
    }

    void setInputRangeInMinutes(int min, int max) {
        logger.v(LOG_TAG, "setting periodicity picker input range to between " + min + " and " + max + " minutes.");

        periodicityPicker.setMinValue(min);
        periodicityPicker.setMaxValue(max);
    }
}