package com.rrm.learnification.jobscheduler;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;

import com.rrm.learnification.common.AndroidLogger;

import java.util.List;

public class AndroidJobScheduler implements JobScheduler {
    private static final String LOG_TAG = "AndroidJobScheduler";

    private final Context context;
    private final JobIdGenerator jobIdGenerator;
    private AndroidLogger logger = new AndroidLogger();

    public AndroidJobScheduler(Context context, JobIdGenerator jobIdGenerator) {
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

    @Override
    public boolean hasPendingJob(Class<?> serviceClass) {
        android.app.job.JobScheduler systemJobScheduler = context.getSystemService(android.app.job.JobScheduler.class);
        List<JobInfo> pendingJobs = systemJobScheduler.getAllPendingJobs();
        for (JobInfo pendingJob : pendingJobs) {
            String pendingJobServiceClassName = pendingJob.getService().getClassName();
            logger.v(LOG_TAG, "checking pending job '" + pendingJobServiceClassName + "'");
            if (pendingJobServiceClassName.equals(serviceClass.getName())) {
                return true;
            }
        }
        return false;
    }
}
