package com.rrm.learnification.common;

interface OnClickCommand {
    static OnClickCommand doNothingOnClickCommand() {
        return () -> {
        };
    }

    void onClick();
}
