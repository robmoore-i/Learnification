package com.rrm.learnification;

class AddLearningItemButton extends AndroidButton {
    AddLearningItemButton(AndroidLogger logger, AddLearningItemView addLearningItemView) {
        super(logger, addLearningItemView.addLearningItemButton());
    }

    @Override
    boolean enabledInitially() {
        return false;
    }
}
