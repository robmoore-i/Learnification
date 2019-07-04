package com.rrm.learnification;

import android.view.View;

interface MainActivityView {
    LearnificationListViewAdaptor createLearnificationListDataBinding(OnSwipeCommand onSwipeCommand, LearnificationRepository learnificationRepository);

    LearningItem getTextInput();

    void setLearnificationButtonOnClickListener(View.OnClickListener onClickListener);

    void setPeriodicityPickerInputRangeInMinutes(int min, int max);
}
