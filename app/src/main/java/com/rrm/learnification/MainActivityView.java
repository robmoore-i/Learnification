package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

interface MainActivityView {
    FloatingActionButton getLearnificationButton();

    LearnificationListViewAdaptor getLearnificationList(OnSwipeCommand onSwipeCommand, LearnificationRepository learnificationRepository);

    LearningItem getTextInput();

    void setLearnificationButtonOnClickListener(View.OnClickListener onClickListener);
}
