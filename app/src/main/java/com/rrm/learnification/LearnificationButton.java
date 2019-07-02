package com.rrm.learnification;

class LearnificationButton {
    private static final String LOG_TAG = "LearnificationButton";

    private final AndroidLogger androidLogger;
    private final MainActivityView mainActivityView;
    private OnClickCommand onClickCommand;

    LearnificationButton(AndroidLogger androidLogger, MainActivityView mainActivityView) {
        this.androidLogger = androidLogger;
        this.mainActivityView = mainActivityView;
    }

    void setOnClickHandler(final OnClickCommand onClickCommand) {
        this.onClickCommand = onClickCommand;
        mainActivityView.setLearnificationButtonOnClickListener(view -> {
            androidLogger.v(LOG_TAG, "add-learnification-button clicked");
            click();
        });
    }

    void click() {
        onClickCommand.onClick();
    }
}
