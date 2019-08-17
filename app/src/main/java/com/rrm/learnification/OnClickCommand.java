package com.rrm.learnification;

interface OnClickCommand {
    static OnClickCommand doNothingOnClickCommand() {
        return () -> {
        };
    }

    void onClick();
}
