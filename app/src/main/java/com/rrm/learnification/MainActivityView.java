package com.rrm.learnification;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.NumberPicker;

interface MainActivityView {
    LearnificationListViewAdaptor createLearnificationListViewDataBinding(OnSwipeCommand onSwipeCommand, LearnificationRepository learnificationRepository);

    LearningItem getLearningItemTextInput();

    NumberPicker periodicityPicker();

    Button addLearningItemButton();

    Toolbar toolbar();

    void setSupportActionBar(Toolbar toolbar);
}
