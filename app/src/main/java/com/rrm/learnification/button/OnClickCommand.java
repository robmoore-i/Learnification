package com.rrm.learnification.button;

public interface OnClickCommand {
    static OnClickCommand doNothingOnClickCommand() {
        return () -> {
        };
    }

    void onClick();
}
