package com.rrm.learnification.jobs;

import android.app.job.JobInfo;
import android.os.PersistableBundle;

import com.rrm.learnification.table.Table;
import com.rrm.learnification.time.AndroidClock;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

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

    public Optional<Long> msUntilExecution(LocalDateTime now) {
        long millis = ChronoUnit.MILLIS.between(now, scheduledExecutionTime());
        if (millis < 0) {
            return Optional.empty();
        }
        return Optional.of(millis);
    }

    public LocalDateTime scheduledExecutionTime() {
        return timeOfScheduling.plusSeconds(earliestStartTimeDelayMs / 1000);
    }

    public boolean isForService(Class<?> serviceClass) {
        return serviceClassName.equals(serviceClass.getName());
    }

    public void addAsRowOf(AndroidClock clock, Table table) {
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

    static Comparator<PendingJob> compareByExecutionTime(LocalDateTime now) {
        return (j1, j2) -> Long.compare(
                // We use Long.MIN_VALUE here as the default value, because if the
                // msUntilExecution(now) call returns an Optional.empty(), then that
                // means the job execution has either happened, or is presently happening.
                // In this case, we would consider that to be the next job, because it's
                // the next job that the user will see, even if it's not the next
                // according to the internal knowledge of the software.
                j1.msUntilExecution(now).orElse(Long.MIN_VALUE),
                j2.msUntilExecution(now).orElse(Long.MIN_VALUE));
    }

    static Predicate<PendingJob> filterByServiceClass(Class<?> serviceClass) {
        return pendingJob -> pendingJob.isForService(serviceClass);
    }
}
