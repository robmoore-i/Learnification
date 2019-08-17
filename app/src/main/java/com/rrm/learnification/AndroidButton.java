package com.rrm.learnification;

interface AndroidButton {
    void addOnClickHandler(OnClickCommand onClickCommand);

    void enable();

    void disable();
}
