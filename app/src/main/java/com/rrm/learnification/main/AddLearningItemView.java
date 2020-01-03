package com.rrm.learnification.main;

import android.widget.Button;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.textinput.OnSubmitTextCommand;
import com.rrm.learnification.textinput.OnTextChangeListener;

interface AddLearningItemView {
    LearningItem addLearningItemTextInput();

    Button addLearningItemButton();

    void addLearningItemOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void addLearningItemOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void addLearningItemClearTextInput();
}
