package com.rrm.learnification.main;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.textinput.OnSubmitTextAction;
import com.rrm.learnification.textinput.OnTextChangeListener;
import com.rrm.learnification.textinput.TextInput;

class LearningItemTextInput implements TextInput {
    private final AddLearningItemView addLearningItemView;

    LearningItemTextInput(AddLearningItemView addLearningItemView) {
        this.addLearningItemView = addLearningItemView;
    }

    LearningItem getLearningItem() {
        return addLearningItemView.getLearningItemTextInput();
    }

    @Override
    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        addLearningItemView.setOnTextChangeListener(onTextChangeListener);
    }

    @Override
    public void setOnSubmitAction(OnSubmitTextAction onSubmitTextAction) {
        addLearningItemView.setOnLearningItemSubmitAction(onSubmitTextAction);
    }

    @Override
    public void clear() {
        addLearningItemView.clearTextInput();
    }
}
