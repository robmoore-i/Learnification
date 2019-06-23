package com.rrm.learnification;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class LearnificationSchedulerService extends JobService {
    private static final String LOG_TAG = "LearnificationSchedulerService";

    private final AndroidLogger androidLogger = new AndroidLogger();

    @Override
    public boolean onStartJob(JobParameters params) {
        androidLogger.v(LOG_TAG, "Job started");
        AndroidNotificationManager androidNotificationManager = new AndroidNotificationManager(this, MainActivity.CHANNEL_ID, NotificationIdGenerator.getInstance(), androidLogger);

        androidNotificationManager.createNotification();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        androidLogger.v(LOG_TAG, "Job stopped");
        return false;
    }
}
