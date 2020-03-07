package com.rrm.learnification.learningitemseteditor;

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

    @Override
    public void clear() {
        addLearningItemView.addLearningItemClearTextInput();
    }

    LearningItem getLearningItem() {
        return addLearningItemView.addLearningItemTextInput();
    }
}