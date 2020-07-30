package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.button.LogicalButton;
import com.rrm.learnification.logger.AndroidLogger;

public class AddLearningItemButton extends LogicalButton {
    public AddLearningItemButton(AndroidLogger logger, AddLearningItemView addLearningItemView) {
        super(logger, addLearningItemView.addLearningItemButton(), "Add Learning Item", false, true);
    }
}
