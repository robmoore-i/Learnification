package com.rrm.learnification;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

class AndroidScheduler implements Scheduler {
    private final Context context;
    private final JobIdGenerator jobIdGenerator;

    AndroidScheduler(Context context, JobIdGenerator jobIdGenerator) {
        this.context = context;
        this.jobIdGenerator = jobIdGenerator;
    }

    @Override
    public void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass) {
        JobInfo.Builder builder = new JobInfo.Builder(jobIdGenerator.nextJobId(), new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false);
        context.getSystemService(JobScheduler.class).schedule(builder.build());
    }
}
