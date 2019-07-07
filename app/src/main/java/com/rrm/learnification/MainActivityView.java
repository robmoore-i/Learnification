package com.rrm.learnification;

import android.view.View;
import android.widget.NumberPicker;

interface MainActivityView {
    LearnificationListViewAdaptor createLearnificationListViewDataBinding(OnSwipeCommand onSwipeCommand, LearnificationRepository learnificationRepository);

    LearningItem getLearningItemTextInput();

    void setLearnificationButtonOnClickListener(View.OnClickListener onClickListener);

    void setPeriodicityPickerInputRangeInMinutes(int min, int max);

    void setPeriodicityPickerOnValuePickedListener(OnValuePickedCommand onValuePickedCommand);

    void setPeriodicityPickerChoiceFormatter(NumberPicker.Formatter formatter);
}
