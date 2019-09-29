package com.rrm.learnification.common;

class PeriodicityRange {
    final int earliestStartTimeDelayMs;
    final int latestStartTimeDelayMs;

    PeriodicityRange(int earliestStartTimeDelayMs, int latestStartTimeDelayMs) {
        this.earliestStartTimeDelayMs = earliestStartTimeDelayMs;
        this.latestStartTimeDelayMs = latestStartTimeDelayMs;
    }
}
