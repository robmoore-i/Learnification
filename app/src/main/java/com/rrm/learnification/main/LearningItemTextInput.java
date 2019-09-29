package com.rrm.learnification.main;

import com.rrm.learnification.common.LearningItem;

class LearningItemTextInput {
    private final AddLearningItemView addLearningItemView;

    LearningItemTextInput(AddLearningItemView addLearningItemView) {
        this.addLearningItemView = addLearningItemView;
    }

    LearningItem getLearningItem() {
        return addLearningItemView.getLearningItemTextInput();
    }

    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        addLearningItemView.setOnTextChangeListener(onTextChangeListener);
    }

    void clear() {
        addLearningItemView.clearTextInput();
    }
}
