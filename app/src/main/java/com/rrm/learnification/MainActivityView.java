package com.rrm.learnification;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;

interface MainActivityView {
    LearningItem getLearningItemTextInput();

    Button addLearningItemButton();

    RecyclerView learningItemsList();
}
