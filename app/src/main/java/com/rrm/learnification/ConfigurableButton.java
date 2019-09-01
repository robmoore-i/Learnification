package com.rrm.learnification;

interface ConfigurableButton {
    void addOnClickHandler(OnClickCommand onClickCommand);

    void enable();

    void disable();
}
