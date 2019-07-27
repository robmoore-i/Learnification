package com.rrm.learnification;

class AppToolbar {
    private static final String LOG_TAG = "AppToolbar";

    private final AndroidLogger logger;
    private final MainActivityView mainActivityView;

    AppToolbar(AndroidLogger logger, MainActivityView mainActivityView) {
        this.logger = logger;
        this.mainActivityView = mainActivityView;
    }

    void initialiseToolbar(String title) {
        logger.v(LOG_TAG, "initialising toolbar with title '" + title + "'");

        mainActivityView.initialiseToolbar(title);
    }
}
