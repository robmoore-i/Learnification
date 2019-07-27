package com.rrm.learnification;

import android.widget.NumberPicker;

interface MainActivityView {
    LearnificationListViewAdaptor createLearnificationListViewDataBinding(OnSwipeCommand onSwipeCommand, LearnificationRepository learnificationRepository);

    LearningItem getLearningItemTextInput();

    void setLearnificationButtonOnClickListener(OnClickCommand onClickListener);

    void setPeriodicityPickerInputRangeInMinutes(int min, int max);

    void setPeriodicityPickerOnValuePickedListener(OnValuePickedCommand onValuePickedCommand);

    void setPeriodicityPickerChoiceFormatter(NumberPicker.Formatter formatter);

    void setPeriodicityPickerToValue(int pickerValue);

    void initialiseToolbar(String title);
}
