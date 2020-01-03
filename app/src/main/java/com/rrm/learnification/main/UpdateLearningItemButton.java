package com.rrm.learnification.main;

import com.rrm.learnification.button.AndroidButton;
import com.rrm.learnification.logger.AndroidLogger;

class UpdateLearningItemButton extends AndroidButton {
    UpdateLearningItemButton(AndroidLogger logger, UpdateLearningItemView view) {
        super(logger, view.updateLearningItemButton());
    }

    @Override
    public boolean enabledInitially() {
        return false;
    }
}
