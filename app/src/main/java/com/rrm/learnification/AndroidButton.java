package com.rrm.learnification;

interface AndroidButton {
    void setOnClickHandler(OnClickCommand onClickCommand);

    void enable();

    void disable();
}
