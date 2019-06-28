package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

class LearnificationButton {
    private static final String LOG_TAG = "LearnificationButton";

    private final AndroidLogger androidLogger;
    private final MainActivityView mainActivityView;

    LearnificationButton(AndroidLogger androidLogger, MainActivityView mainActivityView) {
        this.androidLogger = androidLogger;
        this.mainActivityView = mainActivityView;
    }

    void setOnClickHandler(final OnClickCommand onClickCommand) {
        FloatingActionButton button = mainActivityView.getLearnificationButton();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidLogger.v(LOG_TAG, "add-learnification-button clicked");
                onClickCommand.onClick();
            }
        });
    }
}
