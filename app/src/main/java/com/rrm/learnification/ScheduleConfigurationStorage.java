package com.rrm.learnification;

class ScheduleConfigurationStorage {
    static final int defaultEarliestStartTimeDelayMs = 5000;
    static final int defaultLatestStartTimeDelayMs = 10000;

    PeriodicityRange getPeriodicityRange() {
        return new PeriodicityRange(defaultEarliestStartTimeDelayMs, defaultLatestStartTimeDelayMs);
    }
}
