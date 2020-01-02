package com.rrm.learnification.radiogroup;

public interface RadioGroupView<T> {
    void checkOption(int radioButtonViewId);

    int viewIdOfCheckedOption();

    RadioGroupMappings<T> radioGroupMappings();

    void bindActionsToRadioGroupOptions(RadioGroupMappings<T> radioGroupMappings);
}
