package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.button.AndroidButton;
import com.rrm.learnification.logger.AndroidLogger;

public class AddLearningItemButton extends AndroidButton {
    public AddLearningItemButton(AndroidLogger logger, AddLearningItemView addLearningItemView) {
        super(logger, addLearningItemView.addLearningItemButton(), false);
    }
}
