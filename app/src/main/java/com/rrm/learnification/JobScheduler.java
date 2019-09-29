package com.rrm.learnification;

interface JobScheduler {
    void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass);
}
