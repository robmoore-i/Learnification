package com.rrm.learnification.common;

public interface ConfigurableButton {
    void addOnClickHandler(OnClickCommand onClickCommand);

    void enable();

    void disable();
}
