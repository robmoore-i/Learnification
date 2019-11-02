package com.rrm.learnification.common;

public interface ConfigurableButton {
    void addOnClickHandler(OnClickCommand onClickCommand);

    void clearOnClickHandlers();

    void enable();

    void disable();

    void click();
}
