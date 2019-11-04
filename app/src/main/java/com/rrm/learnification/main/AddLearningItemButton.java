package com.rrm.learnification.main;

import com.rrm.learnification.button.AndroidButton;
import com.rrm.learnification.logger.AndroidLogger;

class AddLearningItemButton extends AndroidButton {
    AddLearningItemButton(AndroidLogger logger, AddLearningItemView addLearningItemView) {
        super(logger, addLearningItemView.addLearningItemButton());
    }

    @Override
    public boolean enabledInitially() {
        return false;
    }
}
