package com.rrm.learnification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v4.app.NotificationManagerCompat;

import java.util.List;

public class LearnificationSchedulerService extends JobService {
    private static final String LOG_TAG = "LearnificationSchedulerService";

    private final AndroidLogger logger = new AndroidLogger();

    @Override
    public boolean onStartJob(JobParameters params) {
        logger.v(LOG_TAG, "Job started");
        AndroidNotificationFactory androidNotificationFactory = new AndroidNotificationFactory(this);
        AndroidLearnificationFactory androidLearnificationFactory = new AndroidLearnificationFactory(logger, androidNotificationFactory);
        final AndroidInternalStorageAdaptor androidInternalStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        LearnificationRepository learnificationRepository = new PersistentLearnificationRepository(logger, new FromFileLearnificationStorage(logger, androidInternalStorageAdaptor));

        List<LearningItem> learningItems = learnificationRepository.learningItems();
        for (LearningItem learningItem : learningItems) {
            logger.v(LOG_TAG, "using learning item '" + learningItem.asSingleString() + "'");
        }

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
