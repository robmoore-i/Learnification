package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.learningitemseteditor.OnSubmitTextCommand;
import com.rrm.learnification.learningitemseteditor.OnTextChangeListener;

public interface AddLearningItemTextInputView {
    LearningItemText addLearningItemTextInput();

    void addLearningItemOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void addLearningItemOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void addLearningItemClearTextInput();
}
