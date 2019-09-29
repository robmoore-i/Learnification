package com.rrm.learnification.common;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;

class AndroidJobScheduler implements JobScheduler {
    private final Context context;
    private final JobIdGenerator jobIdGenerator;

    AndroidJobScheduler(Context context, JobIdGenerator jobIdGenerator) {
        this.context = context;
        this.jobIdGenerator = jobIdGenerator;
    }

    @Override
    public void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass) {
        JobInfo.Builder builder = new JobInfo.Builder(jobIdGenerator.nextJobId(), new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false);
        context.getSystemService(android.app.job.JobScheduler.class).schedule(builder.build());
    }
}
