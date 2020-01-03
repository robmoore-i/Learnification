package com.rrm.learnification.main;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.textinput.OnSubmitTextCommand;
import com.rrm.learnification.textinput.OnTextChangeListener;
import com.rrm.learnification.textinput.TextInput;

class LearningItemTextInput implements TextInput {
    private final AddLearningItemView addLearningItemView;

    LearningItemTextInput(AddLearningItemView addLearningItemView) {
        this.addLearningItemView = addLearningItemView;
    }

    @Override
    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        addLearningItemView.addLearningItemOnTextChangeListener(onTextChangeListener);
    }

    @Override
    public void setOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand) {
        addLearningItemView.addLearningItemOnSubmitTextCommand(onSubmitTextCommand);
    }

    LearningItem getLearningItem() {
        return addLearningItemView.addLearningItemTextInput();
    }

    @Override
    public void clear() {
        addLearningItemView.addLearningItemClearTextInput();
    }
}
