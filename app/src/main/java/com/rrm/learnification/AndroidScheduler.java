package com.rrm.learnification;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

class AndroidScheduler implements Scheduler {
    private final Context context;

    AndroidScheduler(Context context) {
        this.context = context;
    }

    @Override
    public void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass) {
        JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false);
        context.getSystemService(JobScheduler.class).schedule(builder.build());
    }

}
