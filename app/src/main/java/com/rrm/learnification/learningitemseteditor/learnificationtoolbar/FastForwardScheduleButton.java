package com.rrm.learnification.learningitemseteditor.learnificationtoolbar;

import com.rrm.learnification.button.ReifiedButton;
import com.rrm.learnification.logger.AndroidLogger;

public class FastForwardScheduleButton extends ReifiedButton {
    public FastForwardScheduleButton(AndroidLogger logger, ToolbarView toolbarView) {
        super(logger, toolbarView.toolbarButton(), ">>", false);
    }
}
