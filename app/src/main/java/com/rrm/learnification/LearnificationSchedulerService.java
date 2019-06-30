package com.rrm.learnification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v4.app.NotificationManagerCompat;

public class LearnificationSchedulerService extends JobService {
    private static final String LOG_TAG = "LearnificationSchedulerService";

    private final AndroidLogger androidLogger = new AndroidLogger();

    @Override
    public boolean onStartJob(JobParameters params) {
        androidLogger.v(LOG_TAG, "Job started");
        AndroidLearnificationFactory androidLearnificationFactory = new AndroidLearnificationFactory(new AndroidLearnificationFactoryContext(this), MainActivity.CHANNEL_ID, androidLogger);
        LearnificationRepository learnificationRepository = PersistentLearnificationRepository.loadInstance(new AndroidStorage(this), androidLogger);
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learnificationRepository);
        AndroidLearnificationPublisher androidLearnificationPublisher = new AndroidLearnificationPublisher(androidLearnificationFactory, NotificationIdGenerator.getInstance(), learnificationTextGenerator, NotificationManagerCompat.from(this));

        androidLearnificationPublisher.createLearnification();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        androidLogger.v(LOG_TAG, "Job stopped");
        return false;
    }
}
