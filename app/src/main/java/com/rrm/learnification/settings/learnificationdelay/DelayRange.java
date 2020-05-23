package com.rrm.learnification.settings.learnificationdelay;

public class DelayRange {
    public final int earliestStartTimeDelayMs;
    public final int latestStartTimeDelayMs;

    public DelayRange(int earliestStartTimeDelayMs, int latestStartTimeDelayMs) {
        this.earliestStartTimeDelayMs = earliestStartTimeDelayMs;
        this.latestStartTimeDelayMs = latestStartTimeDelayMs;
    }

    public static DelayRange getImminentDelayRange() {
        return new DelayRange(1, 1);
    }
}
