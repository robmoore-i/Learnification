package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.learningitemseteditor.buttonbinding.OnFocusGainedCommand;
import com.rrm.learnification.learningitemseteditor.learningitemlist.dynamicbuttons.OnTextChangeListener;

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
    public void setOnFocusGainedListener(OnFocusGainedCommand onFocusGainedCommand) {
        addLearningItemView.addLearningItemOnFocusTextBoxListener(onFocusGainedCommand);
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
