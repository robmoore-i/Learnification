package com.rrm.learnification;

import java.sql.Time;

interface Scheduler {
    void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass);

    void scheduleTomorrow(Class<?> serviceClass, Time time);
}
