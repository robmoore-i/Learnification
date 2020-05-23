package com.rrm.learnification.settings.radiogroup;

public interface RadioGroupView<T> {
    void checkOption(int radioButtonViewId);

    int viewIdOfCheckedOption();

    RadioGroupMappings<T> radioGroupMappings();

    void bindActionsToRadioGroupOptions(RadioGroupMappings<T> radioGroupMappings);
}
