package com.rrm.learnification;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;

class AndroidJobSchedulerContext {
    private Context context;

    AndroidJobSchedulerContext(Context context) {
        this.context = context;
    }

    ComponentName schedulerServiceComponentName(Class<? extends JobService> schedulerServiceClass) {
        return new ComponentName(context, schedulerServiceClass);
    }

    JobScheduler getJobScheduler() {
        return context.getSystemService(JobScheduler.class);
    }
}
