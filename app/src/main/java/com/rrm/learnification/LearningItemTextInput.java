package com.rrm.learnification;

class LearningItemTextInput {
    private final AddLearningItemView addLearningItemView;

    LearningItemTextInput(AddLearningItemView addLearningItemView) {
        this.addLearningItemView = addLearningItemView;
    }

    LearningItem getLearningItem() {
        return addLearningItemView.getLearningItemTextInput();
    }
}
