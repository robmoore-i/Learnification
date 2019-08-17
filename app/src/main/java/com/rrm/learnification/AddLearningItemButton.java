package com.rrm.learnification;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Button;

class AddLearningItemButton implements AndroidButton {
    private static final String LOG_TAG = "AddLearningItemButton";

    private final AndroidLogger logger;
    private final Button button;
    private OnClickCommand onClickCommand;
    private boolean activated;

    AddLearningItemButton(AndroidLogger logger, AddLearningItemView addLearningItemView) {
        this.logger = logger;
        this.button = addLearningItemView.addLearningItemButton();
        disable();
    }

    @Override
    public void setOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommand = onClickCommand;
        if (activated) {
            bindClickListenerToButton(onClickCommand);
        }
    }

    @Override
    public void enable() {
        activated = true;
        button.getBackground().setColorFilter(Color.parseColor("#32CD32"), PorterDuff.Mode.MULTIPLY);
        button.setClickable(true);
        bindClickListenerToButton(onClickCommand);
    }

    @Override
    public void disable() {
        activated = false;
        button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        button.setClickable(false);
        bindClickListenerToButton(OnClickCommand.doNothingOnClickCommand());
    }

    private void bindClickListenerToButton(OnClickCommand onClickCommand) {
        button.setOnClickListener(view -> {
            logger.v(LOG_TAG, "add-learning-item-button clicked");
            onClickCommand.onClick();
        });
    }
}
