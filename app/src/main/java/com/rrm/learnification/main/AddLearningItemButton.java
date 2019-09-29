package com.rrm.learnification.main;

import com.rrm.learnification.common.AndroidButton;
import com.rrm.learnification.common.AndroidLogger;

class AddLearningItemButton extends AndroidButton {
    AddLearningItemButton(AndroidLogger logger, AddLearningItemView addLearningItemView) {
        super(logger, addLearningItemView.addLearningItemButton());
    }

    @Override
    public boolean enabledInitially() {
        return false;
    }
}
