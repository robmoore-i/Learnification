package com.rrm.learnification.toolbar;

import com.rrm.learnification.common.AndroidLogger;

public class AppToolbar {
    private static final String LOG_TAG = "AppToolbar";

    private final AndroidLogger logger;
    private final ToolbarView toolbarView;

    public AppToolbar(AndroidLogger logger, ToolbarView toolbarView) {
        this.logger = logger;
        this.toolbarView = toolbarView;
    }

    public void setTitle(String title) {
        logger.v(LOG_TAG, "setting toolbar title to '" + title + "'");

        toolbarView.updateToolbar(title);
    }
}
