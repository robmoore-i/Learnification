package com.rrm.learnification.jobs;

import java.util.Optional;
import java.util.function.Predicate;

public interface JobScheduler {
    void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass);

    Optional<Long> msUntilNextJob(Class<?> serviceClass);

    boolean anyJobMatches(Predicate<PendingJob> predicate);

    boolean hasPendingJobInTimeframe(Class<?> serviceClass, int earliestStartTimeDelayMs);

    boolean isAnythingScheduledForTomorrow();

    void triggerNext(Class<?> serviceClass);

    void clearSchedule();
}
