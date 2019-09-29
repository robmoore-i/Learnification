package com.rrm.learnification.common;

interface ConfigurableButton {
    void addOnClickHandler(OnClickCommand onClickCommand);

    void enable();

    void disable();
}
