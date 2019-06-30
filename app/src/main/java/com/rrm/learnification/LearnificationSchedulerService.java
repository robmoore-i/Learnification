package com.rrm.learnification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v4.app.NotificationManagerCompat;

public class LearnificationSchedulerService extends JobService {
    private static final String LOG_TAG = "LearnificationSchedulerService";

    private final AndroidLogger logger = new AndroidLogger();

    @Override
    public boolean onStartJob(JobParameters params) {
        logger.v(LOG_TAG, "Job started");
        AndroidLearnificationFactoryContext androidLearnificationFactoryContext = new AndroidLearnificationFactoryContext(this);
        AndroidLearnificationFactory androidLearnificationFactory = new AndroidLearnificationFactory(logger, androidLearnificationFactoryContext);
        final AndroidStorage androidStorage = new AndroidStorage(logger, this);
        LearnificationRepository learnificationRepository = new PersistentLearnificationRepository(logger, new FromFileLearnificationStorage(logger, androidStorage));
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learnificationRepository);
        AndroidLearnificationPublisher androidLearnificationPublisher = new AndroidLearnificationPublisher(androidLearnificationFactory, NotificationIdGenerator.getInstance(), learnificationTextGenerator, NotificationManagerCompat.from(this));

        androidLearnificationPublisher.createLearnification();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        logger.v(LOG_TAG, "Job stopped");
        return false;
    }
}
