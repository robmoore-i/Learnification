package com.rrm.learnification.common;

import android.support.v7.widget.Toolbar;

public class AppToolbar {
    private static final String LOG_TAG = "AppToolbar";

    private final AndroidLogger logger;
    private final Toolbar toolbar;

    public AppToolbar(AndroidLogger logger, ToolbarView toolbarView) {
        this.logger = logger;
        this.toolbar = toolbarView.toolbar();
    }

    public void setTitle(String title) {
        logger.v(LOG_TAG, "initialising toolbar with title '" + title + "'");

        toolbar.setTitle(title);
    }
}
