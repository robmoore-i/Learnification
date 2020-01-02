package com.rrm.learnification.settings.learnificationpromptstrategy;

import com.rrm.learnification.radiogroup.RadioGroupMappings;

public interface LearnificationPromptStrategyRadioGroupView {
    void bindLearnificationPromptStrategyRadioGroup(RadioGroupMappings<LearnificationPromptStrategy> radioGroupMappings);

    void checkLearnificationPromptStrategy(int radioButtonViewId);

    RadioGroupMappings<LearnificationPromptStrategy> radioGroupMappings();
}
