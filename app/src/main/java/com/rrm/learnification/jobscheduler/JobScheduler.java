package com.rrm.learnification.jobscheduler;

public interface JobScheduler {
    void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass);
}
