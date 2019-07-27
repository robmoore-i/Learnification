package com.rrm.learnification;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.NumberPicker;

interface MainActivityView {
    LearningItem getLearningItemTextInput();

    NumberPicker periodicityPicker();

    Button addLearningItemButton();

    Toolbar toolbar();

    RecyclerView learningItemsList();
}
