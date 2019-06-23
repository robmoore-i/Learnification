package com.rrm.learnification;

import android.app.job.JobInfo;
import android.content.ComponentName;

class LearnificationScheduler {
    private final AndroidJobSchedulerContext androidJobSchedulerContext;

    LearnificationScheduler(AndroidJobSchedulerContext androidJobSchedulerContext) {
        this.androidJobSchedulerContext = androidJobSchedulerContext;
    }

    // schedule the start of the service every 10-20 seconds
    void scheduleJob(int earliestStartTimeDelayMs, int latestStartTimeDelayMs) {
        ComponentName componentName = androidJobSchedulerContext.schedulerServiceComponent();
        JobInfo.Builder builder = new JobInfo.Builder(0, componentName)
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false);
        androidJobSchedulerContext.getJobScheduler().schedule(builder.build());
    }
}
