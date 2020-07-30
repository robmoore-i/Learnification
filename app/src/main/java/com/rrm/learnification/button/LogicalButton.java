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

/**
 * These objects are logical in that they are not necessarily physical all the time.
 * They can be attached or detached from real buttons that are present on the screen,
 * meaning that practically speaking, they can be associated with any button from the
 * XML definition of the UI.
 * <p>
 * This property also allows two logical buttons to occupy the same physical button,
 * provided that only one is attached at any one time. This is the property that the
 * logical button was created for.
 */
public abstract class LogicalButton implements PostPressEvaluatingButton {
    private final AndroidLogger logger;

    private final Button button;
    private final String text;
    private ButtonColour colour;

    private final List<OnClickCommand> onClickCommands = new ArrayList<>();
    private OnClickCommand lastExecutedOnClickCommand;

    private boolean attached;
    private boolean enabled;

    protected LogicalButton(AndroidLogger logger, Button button, String text, boolean enabledInitially, boolean attachedInitially) {
        this.logger = logger;
        this.button = button;
        this.text = text;

        // The same regardless of attachment, so doesn't need a guard for the attached property.
        setOnFocusHandler();

        this.attached = attachedInitially;
        if (attached) {
            bindText();
        }
        if (enabledInitially) {
            enable();
        } else {
            disable();
        }
    }

    public void attach() {
        this.attached = true;
        bindText();
        bindColour();
        bindClickListeners();
    }

    public void detach() {
        this.attached = false;
    }

    private void bindText() {
        button.setText(text);
    }

    @Override
    public final void addOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommands.add(onClickCommand);
        if (enabled && attached) {
            bindClickListeners();
        }
    }

    @Override
    public void addLastExecutedOnClickHandler(final OnClickCommand onClickCommand) {
        if (lastExecutedOnClickCommand != null)
            throw new IllegalStateException("there is already a last executed on click command registered");
        lastExecutedOnClickCommand = onClickCommand;
        if (enabled && attached) {
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
