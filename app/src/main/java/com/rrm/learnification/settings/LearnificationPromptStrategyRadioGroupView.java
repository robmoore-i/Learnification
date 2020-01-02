package com.rrm.learnification.settings;

import android.util.SparseArray;

import com.rrm.learnification.radiogroup.RadioGroupChangeListener;

interface LearnificationPromptStrategyRadioGroupView {
    SparseArray<LearnificationPromptStrategy> learnificationPromptStrategyRadioGroupOptions();

    void bindLearnificationPromptStrategyRadioGroup(RadioGroupChangeListener<LearnificationPromptStrategy> radioGroupChangeListener);
}
