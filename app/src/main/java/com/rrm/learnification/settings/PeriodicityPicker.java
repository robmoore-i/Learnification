package com.rrm.learnification.settings;

import android.annotation.SuppressLint;
import android.widget.NumberPicker;

import com.rrm.learnification.logger.AndroidLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

class PeriodicityPicker {
    private static final String LOG_TAG = "PeriodicityPicker";

    private final AndroidLogger logger;
    private final NumberPicker periodicityPicker;

    PeriodicityPicker(AndroidLogger logger, PeriodicityPickerView periodicityPickerView) {
        this.logger = logger;
        this.periodicityPicker = periodicityPickerView.periodicityPicker();
    }

    void setInputRangeInMinutes(int min, int max) {
        logger.v(LOG_TAG, "setting periodicity picker input range to between " + min + " and " + max + " minutes.");

        periodicityPicker.setMinValue(min);
        periodicityPicker.setMaxValue(max);
    }

    void setOnValuePickedListener(OnValuePickedCommand onValuePickedCommand) {
        logger.v(LOG_TAG, "setting periodicity onChangeListener");

        periodicityPicker.setOnScrollListener((view, scrollState) -> {
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                final int value = view.getValue();
                onValuePickedCommand.onValuePicked(value * 60);

                // Sometimes the picker reports a value that is off-by-one. If this is the case, then this delayed
                // task will pick up the difference and write that in instead.
                int delayMs = 1000;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int delayedValue = periodicityPicker.getValue();
                        if (Math.abs(delayedValue - value) == 1) {
                            onValuePickedCommand.onValuePicked(delayedValue * 60);
                        }
                    }
                }, delayMs);
            }
        });
    }

    void setChoiceFormatter() {
        logger.v(LOG_TAG, "setting the formatter for the choices on the number picker to say 'x minutes' for all x");

        periodicityPicker.setFormatter(i -> i + " minutes");
        periodicityPicker.setWrapSelectorWheel(false);

        // Use a little hack to ensure that formatter applies correctly on first render.
        // See: https://stackoverflow.com/questions/17708325/android-numberpicker-with-formatter-doesnt-format-on-first-rendering
        try {
            @SuppressLint({"PrivateApi", "DiscouragedPrivateApi"})
            Method method = periodicityPicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(periodicityPicker, true);
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    void setToValue(int pickerValue) {
        logger.v(LOG_TAG, "setting the picker to value " + pickerValue);

        periodicityPicker.setValue(pickerValue);
    }

    int currentValueInSeconds() {
        return periodicityPicker.getValue() * 60;
    }
}