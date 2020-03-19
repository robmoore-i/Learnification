package com.rrm.learnification.learningitemseteditor;

import android.widget.Button;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.textinput.OnSubmitTextCommand;
import com.rrm.learnification.textinput.OnTextChangeListener;

interface AddLearningItemView {
    LearningItemText addLearningItemTextInput();

    Button addLearningItemButton();

    void addLearningItemOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void addLearningItemOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void addLearningItemClearTextInput();
}
