package com.rrm.learnification;

class LearningItemTextInput {
    private final AndroidMainActivityView mainActivityView;

    LearningItemTextInput(AndroidMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    LearningItem getLearningItem() {
        return mainActivityView.getLearningItemTextInput();
    }
}
