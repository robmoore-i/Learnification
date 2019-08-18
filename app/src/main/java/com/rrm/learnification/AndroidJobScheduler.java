package com.rrm.learnification;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

class AndroidJobScheduler {
    private Context context;

    AndroidJobScheduler(Context context) {
        this.context = context;
    }

    @SuppressWarnings("SameParameterValue")
    void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<? extends Service> serviceClass) {
        JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false);
        context.getSystemService(JobScheduler.class).schedule(builder.build());
    }
}
