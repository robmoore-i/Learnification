package com.rrm.learnification.button;

public interface PostPressEvaluatingButton extends ConfigurableButton {
    void addLastExecutedOnClickHandler(final OnClickCommand onClickCommand);
}
