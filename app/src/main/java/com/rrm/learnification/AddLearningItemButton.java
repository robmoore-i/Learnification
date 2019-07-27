package com.rrm.learnification;

import android.widget.Button;

class AddLearningItemButton {
    private static final String LOG_TAG = "AddLearningItemButton";

    private final AndroidLogger logger;
    private final Button button;
    private OnClickCommand onClickCommand;

    AddLearningItemButton(AndroidLogger logger, MainActivityView mainActivityView) {
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
