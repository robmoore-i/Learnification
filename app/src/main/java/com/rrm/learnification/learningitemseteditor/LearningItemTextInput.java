package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.textinput.OnSubmitTextCommand;
import com.rrm.learnification.textinput.OnTextChangeListener;
import com.rrm.learnification.textinput.TextInput;

class LearningItemTextInput implements TextInput {
    private final AddLearningItemTextInputView addLearningItemView;

    LearningItemTextInput(AddLearningItemTextInputView addLearningItemView) {
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

    public LearningItemText getText() {
        return addLearningItemView.addLearningItemTextInput();
    }
}
