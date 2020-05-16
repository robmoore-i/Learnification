package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItemText;

interface AddLearningItemTextInputView {
    LearningItemText addLearningItemTextInput();

    void addLearningItemOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void addLearningItemOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void addLearningItemClearTextInput();
}
