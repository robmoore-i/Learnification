package com.rrm.learnification.settings.learnificationdelay;

import android.annotation.SuppressLint;
import android.widget.NumberPicker;

import com.rrm.learnification.logger.AndroidLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

public class DelayPicker {
    private static final String LOG_TAG = "DelayPicker";

    private final AndroidLogger logger;
    private final NumberPicker delayPicker;

    public DelayPicker(AndroidLogger logger, DelayPickerView delayPickerView) {
        this.logger = logger;
        this.delayPicker = delayPickerView.delayPicker();
    }

    @SuppressWarnings("SameParameterValue")
    public void setInputRangeInMinutes(int min, int max) {
        logger.i(LOG_TAG, "setting delay picker input range to between " + min + " and " + max + " minutes.");

        delayPicker.setMinValue(min);
        delayPicker.setMaxValue(max);
    }

    public void setOnValuePickedListener(OnValuePickedCommand onValuePickedCommand) {
        logger.i(LOG_TAG, "setting delay onChangeListener");

        delayPicker.setOnScrollListener((view, scrollState) -> {
            if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                final int value = view.getValue();
                onValuePickedCommand.onValuePicked(value * 60);

                // Sometimes the picker reports a value that is off-by-one. If this is the case, then this delayed
                // task will pick up the difference and write that in instead.
                int delayMs = 1000;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int delayedValue = delayPicker.getValue();
                        if (Math.abs(delayedValue - value) == 1) {
                            onValuePickedCommand.onValuePicked(delayedValue * 60);
                        }
                    }
                }, delayMs);
            }
        });
    }

    public void setChoiceFormatter() {
        logger.i(LOG_TAG, "setting the formatter for the choices on the number picker to say 'x minutes' for all x");

        delayPicker.setFormatter(i -> i + " minute" + (i == 1 ? "" : "s"));
        delayPicker.setWrapSelectorWheel(false);

        // Use a little hack to ensure that formatter applies correctly on first render.
        // See: https://stackoverflow.com/questions/17708325/android-numberpicker-with-formatter-doesnt-format-on-first-rendering
        try {
            @SuppressLint({"PrivateApi", "DiscouragedPrivateApi"})
            Method method = delayPicker.getClass().getDeclaredMethod("changeValueByOne", boolean.class);
            method.setAccessible(true);
            method.invoke(delayPicker, true);
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setToValue(int pickerValue) {
        logger.i(LOG_TAG, "setting the picker to value " + pickerValue);

        delayPicker.setValue(pickerValue);
    }

    int currentValueInSeconds() {
        return delayPicker.getValue() * 60;
    }
}