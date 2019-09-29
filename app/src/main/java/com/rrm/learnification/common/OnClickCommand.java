package com.rrm.learnification.common;

public interface OnClickCommand {
    static OnClickCommand doNothingOnClickCommand() {
        return () -> {
        };
    }

    void onClick();
}
