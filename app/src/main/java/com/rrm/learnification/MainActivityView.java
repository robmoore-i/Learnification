package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

interface MainActivityView {
    FloatingActionButton getLearnificationButton();

    RecyclerView getLearnificationList();

    LearningItem getTextInput();

    void setLearnificationButtonOnClickListener(View.OnClickListener onClickListener);
}
