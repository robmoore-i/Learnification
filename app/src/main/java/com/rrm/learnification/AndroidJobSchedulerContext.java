package com.rrm.learnification;

import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

class AndroidJobSchedulerContext {
    private Context context;

    AndroidJobSchedulerContext(Context context) {
        this.context = context;
    }

    ComponentName schedulerServiceComponent() {
        return new ComponentName(context, LearnificationSchedulerService.class);
    }

    JobScheduler getJobScheduler() {
        return context.getSystemService(JobScheduler.class);
    }
}
