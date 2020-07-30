package com.rrm.learnification.learningitemseteditor.learnificationtoolbar;

import com.rrm.learnification.button.AndroidButton;
import com.rrm.learnification.logger.AndroidLogger;

public class FastForwardScheduleButton extends AndroidButton {
    public FastForwardScheduleButton(AndroidLogger logger, ToolbarView toolbarView) {
        super(logger, toolbarView.toolbarButton(), ">>", false, true);
    }
}
