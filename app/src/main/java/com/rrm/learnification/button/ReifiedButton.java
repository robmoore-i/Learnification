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

public abstract class ReifiedButton implements ConfigurableButton {
    private final AndroidLogger logger;

    private final Button button;
    private final String text;
    private ButtonColour colour;

    private final List<OnClickCommand> onClickCommands = new ArrayList<>();

    private boolean enabled;

    protected ReifiedButton(AndroidLogger logger, Button button, String text, boolean enabledInitially) {
        this.logger = logger;
        this.button = button;
        this.text = text;

        setOnFocusHandler();
        bindText();
        if (enabledInitially) {
            enable();
        } else {
            disable();
        }
    }

    private void bindText() {
        button.setText(text);
    }

    @Override
    public final void addOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommands.add(onClickCommand);
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
        colour = READY_TO_BE_CLICKED;
        bindColour();
        button.setClickable(true);
        bindClickListeners();
    }

    @Override
    public final void disable() {
        enabled = false;
        colour = GRAYED_OUT;
        bindColour();
        button.setClickable(false);
        unbindClickListeners();
    }

    private void bindColour() {
        colour.applyTo(button);
    }

    @Override
    public void click() {
        button.callOnClick();
    }

    private void bindClickListeners() {
        ArrayList<OnClickCommand> onClickCommands = new ArrayList<>(this.onClickCommands);
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
    private void setOnFocusHandler() {
        button.setOnTouchListener((view, event) -> {
            if (!enabled) {
                colour = GRAYED_OUT;
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                colour = FINGER_DOWN;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                colour = READY_TO_BE_CLICKED;
            }
            colour.applyTo(view);
            return false;
        });
    }
}
