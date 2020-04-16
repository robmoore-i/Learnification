package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.textinput.OnSubmitTextCommand;
import com.rrm.learnification.textinput.OnTextChangeListener;

interface AddLearningItemTextInputView {
    LearningItemText addLearningItemTextInput();

    void addLearningItemOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void addLearningItemOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void addLearningItemClearTextInput();
}
