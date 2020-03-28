package com.rrm.learnification.button;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AndroidButton implements ConfigurableButton {
    private final AndroidLogger logger;
    private final Button button;
    private final List<OnClickCommand> onClickCommands = new ArrayList<>();
    private boolean enabled;
    private OnClickCommand lastExecutedOnClickCommand;

    public AndroidButton(AndroidLogger logger, Button button, boolean enabledInitially) {
        this.logger = logger;
        this.button = button;
        setOnFocusHandler();

        if (enabledInitially) {
            enable();
        } else {
            disable();
        }
    }

    @Override
    public final void addOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommands.add(onClickCommand);
        if (enabled) {
            bindClickListeners();
        }
    }

    @Override
    public final void addLastExecutedOnClickHandler(final OnClickCommand onClickCommand) {
        if (lastExecutedOnClickCommand != null)
            throw new IllegalStateException("there is already a last executed on click command registered");
        lastExecutedOnClickCommand = onClickCommand;
        if (enabled) {
            bindClickListeners();
        }
    }

    @Override
    public void clearOnClickHandlers() {
        onClickCommands.clear();
    }

    @Override
    public final void enable() {
        enabled = true;
        AndroidButton.ButtonColour.setColour(button, AndroidButton.ButtonColour.READY_TO_BE_CLICKED);
        button.setClickable(true);
        bindClickListeners();
    }

    @Override
    public final void disable() {
        enabled = false;
        AndroidButton.ButtonColour.setColour(button, AndroidButton.ButtonColour.GRAYED_OUT);
        button.setClickable(false);
        unbindClickListeners();
    }

    @Override
    public void click() {
        button.callOnClick();
    }

    private void bindClickListeners() {
        ArrayList<OnClickCommand> onClickCommands = new ArrayList<>(this.onClickCommands);
        if (lastExecutedOnClickCommand != null) {
            logger.v(logTag(), "last executed on click command is set");
            onClickCommands.add(lastExecutedOnClickCommand);
        }
        bindClickListeners(onClickCommands);
    }

    private void unbindClickListeners() {
        bindClickListeners(Collections.singletonList(OnClickCommand.doNothingOnClickCommand()));
    }

    private void bindClickListeners(List<OnClickCommand> onClickCommands) {
        button.setOnClickListener(view -> {
            logger.v(logTag(), "clicked");
            onClickCommands.forEach(OnClickCommand::onClick);
        });
    }

    private String logTag() {
        return this.getClass().getSimpleName();
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

    public static class ButtonColour {
        public static final int READY_TO_BE_CLICKED = Color.parseColor("#32CD32");
        public static final int GRAYED_OUT = Color.GRAY;
        static final int FINGER_DOWN = Color.GREEN;

        private static void setColour(View view, int colour) {
            view.getBackground().setColorFilter(colour, PorterDuff.Mode.MULTIPLY);
            view.setTag(colour);
        }
    }
}
