package com.rrm.learnification.settings.learnificationpromptstrategy;

import android.util.SparseArray;

import com.rrm.learnification.radiogroup.RadioGroupChangeListener;

public interface LearnificationPromptStrategyRadioGroupView {
    SparseArray<LearnificationPromptStrategy> learnificationPromptStrategyRadioGroupOptions();

    void bindLearnificationPromptStrategyRadioGroup(RadioGroupChangeListener<LearnificationPromptStrategy> radioGroupChangeListener);
}
