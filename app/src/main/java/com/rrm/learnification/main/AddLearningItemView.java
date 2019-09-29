package com.rrm.learnification.main;

import android.widget.Button;

import com.rrm.learnification.common.LearningItem;

interface AddLearningItemView {
    LearningItem getLearningItemTextInput();

    Button addLearningItemButton();

    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void clearTextInput();
}
