package com.rrm.learnification.button;

public interface ConfigurableButton {
    void addOnClickHandler(OnClickCommand onClickCommand);

    void addLastExecutedOnClickHandler(OnClickCommand onClickCommand);

    void clearOnClickHandlers();

    void enable();

    void disable();

    void click();
}
