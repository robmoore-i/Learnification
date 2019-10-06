package com.rrm.learnification.jobs;

import android.app.job.JobInfo;
import android.os.PersistableBundle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class PendingJob {
    private final String serviceClassName;
    private final long earliestStartTimeDelayMs;
    private final LocalDateTime timeOfScheduling;

    PendingJob(String serviceClassName, long earliestStartTimeDelayMs, LocalDateTime timeOfScheduling) {
        this.serviceClassName = serviceClassName;
        this.earliestStartTimeDelayMs = earliestStartTimeDelayMs;
        this.timeOfScheduling = timeOfScheduling;
    }

    static PendingJob fromJobInfo(JobInfo job) {
        PersistableBundle extras = job.getExtras();
        LocalDateTime timeOfScheduling = LocalDateTime.parse(extras.getString(AndroidJobScheduler.TIME_OF_SCHEDULING));
        return new PendingJob(job.getService().getClassName(), job.getMinLatencyMillis(), timeOfScheduling);
    }

    /**
     * @param serviceClass The service class for the jobs you want to check for
     * @return True, if the PendingJob is for the given serviceClass
     */
    boolean willTriggerService(Class<?> serviceClass) {
        return serviceClassName.equals(serviceClass.getName());
    }

    /**
     * @param maxDelayTimeMs The delay time you're checking against, in milliseconds
     * @return True, if the PendingJob will trigger before the given maxDelayTime elapses.
     */
    boolean hasDelayTimeNoMoreThan(int maxDelayTimeMs) {
        return earliestStartTimeDelayMs <= maxDelayTimeMs;
    }

    Long msUntilExecution(LocalDateTime now) {
        return ChronoUnit.MILLIS.between(now, scheduledExecutionTime());
    }

    LocalDateTime scheduledExecutionTime() {
        return timeOfScheduling.plusSeconds(earliestStartTimeDelayMs / 1000);
    }
}
