package com.rrm.learnification.jobs;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

import com.rrm.learnification.common.AndroidClock;
import com.rrm.learnification.common.AndroidLogger;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public class AndroidJobScheduler implements JobScheduler {
    private static final String LOG_TAG = "AndroidJobScheduler";

    static final String TIME_OF_SCHEDULING = "timeOfScheduling";

    private final AndroidLogger logger;
    private final Context context;
    private final JobIdGenerator jobIdGenerator;
    private final AndroidClock clock;
    private android.app.job.JobScheduler systemJobScheduler;

    public AndroidJobScheduler(AndroidLogger logger, Context context, JobIdGenerator jobIdGenerator, AndroidClock clock) {
        this.logger = logger;
        this.context = context;
        this.systemJobScheduler = context.getSystemService(android.app.job.JobScheduler.class);
        this.jobIdGenerator = jobIdGenerator;
        this.clock = clock;
    }

    @Override
    public void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass) {
        logger.v(LOG_TAG, "scheduling job for serviceClass " + serviceClass.getName() + " in delay range " + earliestStartTimeDelayMs + "-" + latestStartTimeDelayMs);
        JobInfo.Builder builder = new JobInfo.Builder(jobIdGenerator.nextJobId(), new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false)
                .setExtras(jobExtras());
        systemJobScheduler.schedule(builder.build());
    }

    @Override
    public boolean hasPendingJob(Class<?> serviceClass, int maxDelayTimeMs) {
        logger.v(LOG_TAG, "checking for pending job with serviceClass " + serviceClass.getName() + " occurring in the next " + maxDelayTimeMs + "ms");
        return pendingJobs().anyMatch(job -> job.willTriggerService(serviceClass) && job.hasDelayTimeNoMoreThan(maxDelayTimeMs));
    }

    @Override
    public Optional<Long> msUntilNextJob(Class<?> serviceClass) {
        LocalDateTime now = clock.now();
        return pendingJobs()
                .min((j1, j2) -> Long.compare(j1.msUntilExecution(now), j2.msUntilExecution(now)))
                .map(j -> j.msUntilExecution(now));
    }

    @Override
    public boolean isAnythingScheduledForTomorrow() {
        return pendingJobs().anyMatch(j -> (j.scheduledExecutionTime().getDayOfMonth() - clock.now().getDayOfMonth()) == 1);
    }

    private Stream<PendingJob> pendingJobs() {
        return systemJobScheduler
                .getAllPendingJobs()
                .stream()
                .map(PendingJob::fromJobInfo);
    }

    private PersistableBundle jobExtras() {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(TIME_OF_SCHEDULING, clock.now().toString());
        return persistableBundle;
    }
}
