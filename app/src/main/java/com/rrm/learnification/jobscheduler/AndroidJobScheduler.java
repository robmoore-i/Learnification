package com.rrm.learnification.jobscheduler;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;

import com.rrm.learnification.common.AndroidLogger;

import java.util.Optional;
import java.util.stream.Stream;

public class AndroidJobScheduler implements JobScheduler {
    private static final String LOG_TAG = "AndroidJobScheduler";

    private final AndroidLogger logger;
    private final Context context;
    private final JobIdGenerator jobIdGenerator;

    public AndroidJobScheduler(AndroidLogger logger, Context context, JobIdGenerator jobIdGenerator) {
        this.logger = logger;
        this.context = context;
        this.jobIdGenerator = jobIdGenerator;
    }

    @Override
    public void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass) {
        logger.v(LOG_TAG, "scheduling job for serviceClass " + serviceClass.getName() + " in delay range " + earliestStartTimeDelayMs + "-" + latestStartTimeDelayMs);
        JobInfo.Builder builder = new JobInfo.Builder(jobIdGenerator.nextJobId(), new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false);
        context.getSystemService(android.app.job.JobScheduler.class).schedule(builder.build());
    }

    @Override
    public boolean hasPendingJob(Class<?> serviceClass, int maxDelayTimeMs) {
        logger.v(LOG_TAG, "checking for pending job with serviceClass " + serviceClass.getName() + " occurring in the next " + maxDelayTimeMs + "ms");
        return pendingJobs().anyMatch(job -> job.willTriggerService(serviceClass) && job.willTriggerBefore(maxDelayTimeMs));
    }

    @Override
    public Optional<Long> msUntilNextJob(Class<?> serviceClass) {
        return pendingJobs()
                .min((j1, j2) -> Long.compare(j1.delayTime(), j2.delayTime()))
                .map(PendingJob::delayTime);
    }

    private Stream<PendingJob> pendingJobs() {
        return context
                .getSystemService(android.app.job.JobScheduler.class)
                .getAllPendingJobs()
                .stream()
                .map(job -> new PendingJob(job.getService().getClassName(), job.getMinLatencyMillis()));
    }
}
