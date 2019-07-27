package com.rrm.learnification;

import android.widget.Button;

class LearnificationButton {
    private static final String LOG_TAG = "LearnificationButton";

    private final AndroidLogger logger;
    private final Button button;
    private OnClickCommand onClickCommand;

    LearnificationButton(AndroidLogger logger, MainActivityView mainActivityView) {
        this.logger = logger;
        this.button = mainActivityView.addLearningItemButton();
    }

    void setOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommand = onClickCommand;

        button.setOnClickListener(view -> {
            logger.v(LOG_TAG, "add-learning-item-button clicked");
            onClickCommand.onClick();
        });
    }

    void click() {
        logger.v(LOG_TAG, "add-learning-item-button clicked");
        onClickCommand.onClick();
    }
}
