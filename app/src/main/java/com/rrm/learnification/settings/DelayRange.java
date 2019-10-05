package com.rrm.learnification.settings;

public class DelayRange {
    public final int earliestStartTimeDelayMs;
    public final int latestStartTimeDelayMs;

    public DelayRange(int earliestStartTimeDelayMs, int latestStartTimeDelayMs) {
        this.earliestStartTimeDelayMs = earliestStartTimeDelayMs;
        this.latestStartTimeDelayMs = latestStartTimeDelayMs;
    }
}
