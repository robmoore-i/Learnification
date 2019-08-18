package com.rrm.learnification;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AddLearningItemButton implements AndroidButton {
    private static final String LOG_TAG = "AddLearningItemButton";

    private final AndroidLogger logger;
    private final Button button;
    private final List<OnClickCommand> onClickCommands = new ArrayList<>();
    private boolean activated;

    AddLearningItemButton(AndroidLogger logger, AddLearningItemView addLearningItemView) {
        this.logger = logger;
        this.button = addLearningItemView.addLearningItemButton();
        disable();
        setOnFocusHandler();
    }

    @Override
    public void addOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommands.add(onClickCommand);
        if (activated) {
            bindClickListenersToButton(onClickCommands);
        }
    }

    @Override
    public void enable() {
        activated = true;
        ButtonColour.setColour(button, ButtonColour.READY_TO_BE_CLICKED);
        button.setClickable(true);
        bindClickListenersToButton(onClickCommands);
    }

    @Override
    public void disable() {
        activated = false;
        ButtonColour.setColour(button, ButtonColour.GRAYED_OUT);
        button.setClickable(false);
        bindClickListenersToButton(Collections.singletonList(OnClickCommand.doNothingOnClickCommand()));
    }

    private void bindClickListenersToButton(List<OnClickCommand> onClickCommands) {
        button.setOnClickListener(view -> {
            logger.v(LOG_TAG, "add-learning-item-button clicked");
            onClickCommands.forEach(OnClickCommand::onClick);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnFocusHandler() {
        button.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && activated) {
                ButtonColour.setColour(view, ButtonColour.FINGER_DOWN);
            } else if (event.getAction() == MotionEvent.ACTION_UP && activated) {
                ButtonColour.setColour(view, ButtonColour.READY_TO_BE_CLICKED);
            } else if (!activated) {
                ButtonColour.setColour(view, ButtonColour.GRAYED_OUT);
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
