package com.rrm.learnification.main;

import android.widget.Button;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.textinput.OnSubmitTextCommand;
import com.rrm.learnification.textinput.OnTextChangeListener;

interface AddLearningItemView {
    LearningItem getLearningItemTextInput();

    Button addLearningItemButton();

    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void clearTextInput();

    void setOnLearningItemInputSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);
}
