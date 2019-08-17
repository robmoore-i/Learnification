package com.rrm.learnification;

import android.widget.Button;

interface AddLearningItemView {
    LearningItem getLearningItemTextInput();

    Button addLearningItemButton();

    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void clearTextInput();
}
