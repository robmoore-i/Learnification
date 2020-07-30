package com.rrm.learnification.jobs;

import com.rrm.learnification.table.Table;

import java.util.Optional;
import java.util.function.Predicate;

public interface JobScheduler {
    void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass);

    Optional<Long> msUntilNextJob(Class<?> serviceClass);

    boolean anyJobMatches(Predicate<PendingJob> predicate);

    boolean hasPendingJobInTimeframe(Class<?> serviceClass, int earliestStartTimeDelayMs);

    boolean hasPendingJobForTomorrow(Class<?> serviceClass);

    void clearSchedule();

    void triggerNext(Class<?> serviceClass);

    void insertJobInfoInto(Table table);
}
