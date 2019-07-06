package com.rrm.learnification;

class PeriodicityPicker {
    private static final String LOG_TAG = "PeriodicityPicker";

    private final AndroidLogger logger;
    private final MainActivityView mainActivityView;

    PeriodicityPicker(AndroidLogger logger, MainActivityView mainActivityView) {
        this.logger = logger;
        this.mainActivityView = mainActivityView;
    }

    void setInputRangeInMinutes(int min, int max) {
        logger.v(LOG_TAG, "setting periodicity picker input range to between " + min + " and " + max + " minutes.");

        mainActivityView.setPeriodicityPickerInputRangeInMinutes(min, max);
    }

    void setOnChangeListener(OnChangeCommand onChangeCommand) {
        logger.v(LOG_TAG, "setting periodicity onChangeListener");

        mainActivityView.setPeriodicityPickerOnChangeListener(onChangeCommand);
    }

    void setChoiceFormatter() {
        logger.v(LOG_TAG, "setting the formatter for the choices on the number picker to say 'x minutes' for all x");

        mainActivityView.setPeriodicityPickerChoiceFormatter(i -> i + " minutes");
    }
}