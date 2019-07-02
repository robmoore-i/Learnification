package com.rrm.learnification;

import android.app.job.JobInfo;

class LearnificationScheduler {
    private static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final AndroidJobSchedulerContext androidJobSchedulerContext;

    LearnificationScheduler(AndroidLogger logger, AndroidJobSchedulerContext androidJobSchedulerContext) {
        this.logger = logger;
        this.androidJobSchedulerContext = androidJobSchedulerContext;
    }

    // schedule the start of the service every 10-20 seconds
    void scheduleJob(PeriodicityRange periodicityRange) {
        int earliestStartTimeDelayMs = periodicityRange.earliestStartTimeDelayMs;
        int latestStartTimeDelayMs = periodicityRange.latestStartTimeDelayMs;

        logger.v(LOG_TAG, "scheduling notification job in the next " + earliestStartTimeDelayMs + " to " + latestStartTimeDelayMs + "ms");

        JobInfo.Builder builder = new JobInfo.Builder(0, androidJobSchedulerContext.schedulerServiceComponentName(LearnificationSchedulerService.class))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false);
        androidJobSchedulerContext.getJobScheduler().schedule(builder.build());
    }
}
