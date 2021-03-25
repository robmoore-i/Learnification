package com.rrm.learnification.jobs;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.learnificationdelay.DelayRange;
import com.rrm.learnification.table.Table;
import com.rrm.learnification.time.AndroidClock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AndroidJobScheduler implements JobScheduler {
    static final String TIME_OF_SCHEDULING = "timeOfScheduling";
    private static final String LOG_TAG = "AndroidJobScheduler";
    private final AndroidLogger logger;
    private final AndroidClock clock;
    private final Context context;
    private final JobIdGenerator jobIdGenerator;
    private final android.app.job.JobScheduler systemJobScheduler;

    public AndroidJobScheduler(AndroidLogger logger, AndroidClock clock, Context context, JobIdGenerator jobIdGenerator) {
        this.logger = logger;
        this.clock = clock;
        this.context = context;
        this.systemJobScheduler = context.getSystemService(android.app.job.JobScheduler.class);
        this.jobIdGenerator = jobIdGenerator;
    }

    @Override
    public int schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass) {
        PersistableBundle jobExtras = new PersistableBundle();
        jobExtras.putString(TIME_OF_SCHEDULING, clock.now().toString());
        int jobId = jobIdGenerator.next();
        JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false)
                .setExtras(jobExtras);
        logger.i(LOG_TAG, "scheduling job (id=" + jobId + ") for serviceClass " + serviceClass.getName() + " " +
                "in delay range " + earliestStartTimeDelayMs + "-" + latestStartTimeDelayMs);
        systemJobScheduler.schedule(builder.build());
        return jobId;
    }

    @Override
    public Optional<Long> msUntilNextJob(Class<?> serviceClass) {
        return nextJob(serviceClass).flatMap(j -> j.msUntilExecution(clock.now()));
    }

    @Override
    public boolean anyJobMatches(Predicate<PendingJob> predicate) {
        return pendingJobs().anyMatch(predicate);
    }

    @Override
    public boolean hasPendingJobInTimeframe(Class<?> serviceClass, int occurringBeforeThisManyMilliseconds) {
        logger.i(LOG_TAG, "checking for pending job with serviceClass " + serviceClass.getName() +
                " occurring in the next " + occurringBeforeThisManyMilliseconds + "ms");
        return anyJobMatches(job -> job.willTriggerService(serviceClass) && job.hasDelayTimeNoMoreThan(occurringBeforeThisManyMilliseconds));
    }

    @Override
    public boolean hasPendingJobForTomorrow(Class<?> serviceClass) {
        return anyJobMatches(j -> j.isForService(serviceClass) && (j.scheduledExecutionTime().getDayOfMonth() - clock.now().getDayOfMonth() == 1));
    }

    @Override
    public void clearSchedule() {
        pendingJobs().forEach(pendingJob -> systemJobScheduler.cancel(pendingJob.id));
    }

    @Override
    public void triggerNext(Class<?> serviceClass) {
        Optional<PendingJob> pendingJob = nextJob(serviceClass);
        if (pendingJob.isPresent()) {
            logger.i(LOG_TAG, "triggering next job");
            int id = pendingJob.get().id;
            systemJobScheduler.cancel(id);
            DelayRange imminentDelayRange = DelayRange.getImminentDelayRange();
            schedule(imminentDelayRange.earliestStartTimeDelayMs, imminentDelayRange.latestStartTimeDelayMs, serviceClass);
        } else {
            logger.i(LOG_TAG, "didn't trigger next job because there isn't one");
        }
    }

    @Override
    public void insertJobInfoInto(Table table) {
        pendingJobs().forEach(pendingJob -> pendingJob.addAsRowOf(clock, table));
    }

    private Stream<PendingJob> pendingJobs() {
        return systemJobScheduler
                .getAllPendingJobs()
                .stream()
                .map(PendingJob::fromJobInfo);
    }

    private Optional<PendingJob> nextJob(Class<?> serviceClass) {
        LocalDateTime now = clock.now();
        return pendingJobs()
                .filter(PendingJob.filterByServiceClass(serviceClass))
                .min(PendingJob.compareByExecutionTime(now));
    }
}
