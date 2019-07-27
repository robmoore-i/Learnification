package com.rrm.learnification;

import android.support.v7.widget.Toolbar;

class AppToolbar {
    private static final String LOG_TAG = "AppToolbar";

    private final AndroidLogger logger;
    private final MainActivityView mainActivityView;
    private final Toolbar toolbar;

    AppToolbar(AndroidLogger logger, MainActivityView mainActivityView) {
        this.logger = logger;
        this.mainActivityView = mainActivityView;
        this.toolbar = mainActivityView.toolbar();
    }

    void initialiseToolbar(String title) {
        logger.v(LOG_TAG, "initialising toolbar with title '" + title + "'");

        toolbar.setTitle(title);
        mainActivityView.setSupportActionBar(toolbar);
    }
}
