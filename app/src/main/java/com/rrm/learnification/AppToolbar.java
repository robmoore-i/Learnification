package com.rrm.learnification;

import android.support.v7.widget.Toolbar;

class AppToolbar {
    private static final String LOG_TAG = "AppToolbar";

    private final AndroidLogger logger;
    private final Toolbar toolbar;

    AppToolbar(AndroidLogger logger, ToolbarView toolbarView) {
        this.logger = logger;
        this.toolbar = toolbarView.toolbar();
    }

    void setTitle(String title) {
        logger.v(LOG_TAG, "initialising toolbar with title '" + title + "'");

        toolbar.setTitle(title);
    }
}
