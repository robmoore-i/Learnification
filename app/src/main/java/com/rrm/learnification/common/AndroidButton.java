package com.rrm.learnification.common;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AndroidButton implements ConfigurableButton {
    private final AndroidLogger logger;
    private final Button button;
    private final List<OnClickCommand> onClickCommands = new ArrayList<>();
    private boolean enabled;

    public AndroidButton(AndroidLogger logger, Button button) {
        this.logger = logger;
        this.button = button;
        setOnFocusHandler();

        if (enabledInitially()) {
            enable();
        } else {
            disable();
        }
    }

    public abstract boolean enabledInitially();

    @Override
    public final void addOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommands.add(onClickCommand);
        if (enabled) {
            bindClickListenersToButton(onClickCommands);
        }
    }

    public void clearOnClickHandlers() {
        onClickCommands.clear();
    }

    @Override
    public final void enable() {
        enabled = true;
        AndroidButton.ButtonColour.setColour(button, AndroidButton.ButtonColour.READY_TO_BE_CLICKED);
        button.setClickable(true);
        bindClickListenersToButton(onClickCommands);
    }

    @Override
    public final void disable() {
        enabled = false;
        AndroidButton.ButtonColour.setColour(button, AndroidButton.ButtonColour.GRAYED_OUT);
        button.setClickable(false);
        bindClickListenersToButton(Collections.singletonList(OnClickCommand.doNothingOnClickCommand()));
    }

    public void click() {
        button.callOnClick();
    }

    private String logTag() {
        return this.getClass().getSimpleName();
    }

    private void bindClickListenersToButton(List<OnClickCommand> onClickCommands) {
        button.setOnClickListener(view -> {
            logger.v(logTag(), "clicked");
            onClickCommands.forEach(OnClickCommand::onClick);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnFocusHandler() {
        button.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && enabled) {
                AndroidButton.ButtonColour.setColour(view, AndroidButton.ButtonColour.FINGER_DOWN);
            } else if (event.getAction() == MotionEvent.ACTION_UP && enabled) {
                AndroidButton.ButtonColour.setColour(view, AndroidButton.ButtonColour.READY_TO_BE_CLICKED);
            } else if (!enabled) {
                AndroidButton.ButtonColour.setColour(view, AndroidButton.ButtonColour.GRAYED_OUT);
            }
            return false;
        });
    }

    private static class ButtonColour {
        private static final int READY_TO_BE_CLICKED = Color.parseColor("#32CD32");
        private static final int GRAYED_OUT = Color.GRAY;
        private static final int FINGER_DOWN = Color.GREEN;

        private static void setColour(View view, int colour) {
            view.getBackground().setColorFilter(colour, PorterDuff.Mode.MULTIPLY);
        }
    }
}
