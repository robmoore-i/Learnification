package com.rrm.learnification.main;

import android.widget.Button;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.OnSubmitTextAction;
import com.rrm.learnification.common.OnTextChangeListener;

interface AddLearningItemView {
    LearningItem getLearningItemTextInput();

    Button addLearningItemButton();

    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void clearTextInput();

    void setOnLearningItemSubmitAction(OnSubmitTextAction onSubmitTextAction);
}
