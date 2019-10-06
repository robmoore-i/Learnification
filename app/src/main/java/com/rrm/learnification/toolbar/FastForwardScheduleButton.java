package com.rrm.learnification.toolbar;

import com.rrm.learnification.common.AndroidButton;
import com.rrm.learnification.common.AndroidLogger;

public class FastForwardScheduleButton extends AndroidButton {
    public FastForwardScheduleButton(AndroidLogger logger, ToolbarView toolbarView) {
        super(logger, toolbarView.toolbarButton());
    }

    @Override
    public boolean enabledInitially() {
        return false;
    }
}
