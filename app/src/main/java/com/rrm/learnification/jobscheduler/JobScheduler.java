package com.rrm.learnification.jobscheduler;

import java.util.Optional;

public interface JobScheduler {
    void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass);

    boolean hasPendingJob(Class<?> serviceClass, int earliestStartTimeDelayMs);

    Optional<Long> msUntilNextJob(Class<?> serviceClass);
}
