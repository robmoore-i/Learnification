package com.rrm.learnification;

import android.graphics.Color;
import android.graphics.PorterDuff;
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
        button.getBackground().setColorFilter(Color.parseColor("#32CD32"), PorterDuff.Mode.MULTIPLY);
        button.setClickable(true);
        bindClickListenersToButton(onClickCommands);
    }

    @Override
    public void disable() {
        activated = false;
        button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        button.setClickable(false);
        bindClickListenersToButton(Collections.singletonList(OnClickCommand.doNothingOnClickCommand()));
    }

    private void bindClickListenersToButton(List<OnClickCommand> onClickCommands) {
        button.setOnClickListener(view -> {
            logger.v(LOG_TAG, "add-learning-item-button clicked");
            onClickCommands.forEach(OnClickCommand::onClick);
        });
    }
}
