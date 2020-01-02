package com.rrm.learnification.jobs;

import java.util.Optional;

public interface JobScheduler {
    void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass);

    boolean hasPendingJob(Class<?> serviceClass, int earliestStartTimeDelayMs);

    Optional<Long> msUntilNextJob(Class<?> serviceClass);

    boolean isAnythingScheduledForTomorrow();

    void triggerNext(Class<?> serviceClass);
}