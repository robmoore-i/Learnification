package com.rrm.learnification.common;

class AddLearningItemButton extends AndroidButton {
    AddLearningItemButton(AndroidLogger logger, AddLearningItemView addLearningItemView) {
        super(logger, addLearningItemView.addLearningItemButton());
    }

    @Override
    public boolean enabledInitially() {
        return false;
    }
}
