package com.rrm.learnification;

class LearnificationButton {
    private static final String LOG_TAG = "LearnificationButton";

    private final AndroidLogger logger;
    private final MainActivityView mainActivityView;
    private OnClickCommand onClickCommand;

    LearnificationButton(AndroidLogger logger, MainActivityView mainActivityView) {
        this.logger = logger;
        this.mainActivityView = mainActivityView;
    }

    void setOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommand = onClickCommand;
        mainActivityView.setLearnificationButtonOnClickListener(onClickCommand);
    }

    void click() {
        logger.v(LOG_TAG, "add-learning-item-button clicked");
        onClickCommand.onClick();
    }
}
