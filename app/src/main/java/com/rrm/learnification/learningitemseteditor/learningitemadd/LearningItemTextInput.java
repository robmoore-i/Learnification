package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.learningitemseteditor.OnSubmitTextCommand;
import com.rrm.learnification.learningitemseteditor.OnTextChangeListener;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.TextInput;

public class LearningItemTextInput implements TextInput {
    private final AddLearningItemTextInputView addLearningItemView;

    public LearningItemTextInput(AddLearningItemTextInputView addLearningItemView) {
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
