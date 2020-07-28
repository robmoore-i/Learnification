package com.rrm.learnification.jobs;

import android.app.job.JobInfo;
import android.os.PersistableBundle;

import com.rrm.learnification.table.AndroidTable;
import com.rrm.learnification.time.AndroidClock;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PendingJob {
    public final int id;
    private final String serviceClassName;
    private final long earliestStartTimeDelayMs;
    private final LocalDateTime timeOfScheduling;

    PendingJob(String serviceClassName, long earliestStartTimeDelayMs, LocalDateTime timeOfScheduling, int id) {
        this.serviceClassName = serviceClassName;
        this.earliestStartTimeDelayMs = earliestStartTimeDelayMs;
        this.timeOfScheduling = timeOfScheduling;
        this.id = id;
    }

    static PendingJob fromJobInfo(JobInfo job) {
        PersistableBundle extras = job.getExtras();
        LocalDateTime timeOfScheduling = LocalDateTime.parse(extras.getString(AndroidJobScheduler.TIME_OF_SCHEDULING));
        return new PendingJob(job.getService().getClassName(), job.getMinLatencyMillis(), timeOfScheduling, job.getId());
    }

    /**
     * @param serviceClass The service class for the jobs you want to check for
     * @return True, if the PendingJob is for the given serviceClass
     */
    public boolean willTriggerService(Class<?> serviceClass) {
        return serviceClassName.equals(serviceClass.getName());
    }

    /**
     * @param maxDelayTimeMs The delay time you're checking against, in milliseconds
     * @return True, if the PendingJob will trigger before the given maxDelayTime elapses.
     */
    public boolean hasDelayTimeNoMoreThan(int maxDelayTimeMs) {
        return earliestStartTimeDelayMs <= maxDelayTimeMs;
    }

    public Long msUntilExecution(LocalDateTime now) {
        return ChronoUnit.MILLIS.between(now, scheduledExecutionTime());
    }

    public LocalDateTime scheduledExecutionTime() {
        return timeOfScheduling.plusSeconds(earliestStartTimeDelayMs / 1000);
    }

    public boolean isForService(Class<?> serviceClass) {
        return serviceClassName.equals(serviceClass.getName());
    }

    public void addAsRowOf(AndroidClock clock, AndroidTable table) {
        ScheduledTimeFormatter scheduledTimeFormatter = new ScheduledTimeFormatter(clock);
        LocalDateTime scheduledExecutionTime = clock.now().plusSeconds(earliestStartTimeDelayMs / 1000);
        String infoText = scheduledTimeFormatter.format(scheduledExecutionTime);
        table.addRow(serviceSimpleName(), infoText);
    }

    private String serviceSimpleName() {
        return serviceSimpleClassName().replaceAll("PublishingService", "");
    }

    private String serviceSimpleClassName() {
        try {
            Class<?> serviceClass = Class.forName(serviceClassName);
            return serviceClass.getSimpleName();
        } catch (ClassNotFoundException ignored) {
            String[] split = serviceClassName.split("[.]");
            return split[split.length - 1];
        }
    }
}
