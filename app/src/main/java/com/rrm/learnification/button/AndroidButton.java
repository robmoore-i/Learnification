package com.rrm.learnification.button;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.widget.Button;

import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.rrm.learnification.button.ButtonColour.FINGER_DOWN;
import static com.rrm.learnification.button.ButtonColour.GRAYED_OUT;
import static com.rrm.learnification.button.ButtonColour.READY_TO_BE_CLICKED;

public abstract class AndroidButton implements ConfigurableButton {
    private final AndroidLogger logger;
    private final Button button;
    private final List<OnClickCommand> onClickCommands = new ArrayList<>();
    private boolean enabled;
    private OnClickCommand lastExecutedOnClickCommand;

    protected AndroidButton(AndroidLogger logger, Button button, boolean enabledInitially) {
        this.logger = logger;
        this.button = button;
        setOnFocusHandler(button);

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
        READY_TO_BE_CLICKED.applyTo(button);
        button.setClickable(true);
        bindClickListeners();
    }

    @Override
    public final void disable() {
        enabled = false;
        GRAYED_OUT.applyTo(button);
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
            logger.i(logTag(), "last executed on click command is set");
            onClickCommands.add(lastExecutedOnClickCommand);
        }
        bindClickListeners(onClickCommands);
    }

    private void unbindClickListeners() {
        bindClickListeners(Collections.singletonList(OnClickCommand.doNothingOnClickCommand()));
    }

    private void bindClickListeners(List<OnClickCommand> onClickCommands) {
        button.setOnClickListener(view -> {
            logger.i(logTag(), "clicked");
            onClickCommands.forEach(OnClickCommand::onClick);
        });
    }

    private String logTag() {
        return this.getClass().getSimpleName();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnFocusHandler(Button button) {
        button.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && enabled) {
                FINGER_DOWN.applyTo(view);
            } else if (event.getAction() == MotionEvent.ACTION_UP && enabled) {
                READY_TO_BE_CLICKED.applyTo(view);
            } else if (!enabled) {
                GRAYED_OUT.applyTo(view);
            }
            return false;
        });
    }
}
