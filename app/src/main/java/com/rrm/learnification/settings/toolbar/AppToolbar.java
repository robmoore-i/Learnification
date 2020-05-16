package com.rrm.learnification.settings.toolbar;

import com.rrm.learnification.logger.AndroidLogger;

public class AppToolbar {
    private static final String LOG_TAG = "AppToolbar";

    private final AndroidLogger logger;
    private final SimpleToolbarView simpleToolbarView;

    public AppToolbar(AndroidLogger logger, SimpleToolbarView simpleToolbarView) {
        this.logger = logger;
        this.simpleToolbarView = simpleToolbarView;
    }

    public void setTitle(String title) {
        logger.i(LOG_TAG, "setting toolbar title to '" + title + "'");

        simpleToolbarView.updateToolbar(title);
    }
}
