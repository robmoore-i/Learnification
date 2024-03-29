package com.rrm.learnification.learnification.publication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.files.AndroidInternalStorageAdaptor;
import com.rrm.learnification.files.FileStorageAdaptor;
import com.rrm.learnification.learnification.creation.LearnificationFactory;
import com.rrm.learnification.learningitemstorage.LearningItemSqlTableClient;
import com.rrm.learnification.learningitemstorage.LearningItemSupplier;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationPublisher;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.PendingIntentIdGenerator;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;

import java.util.List;

public class LearnificationPublishingService extends JobService {
    private static final String LOG_TAG = "LearnificationPublishingService";

    private final AndroidLogger logger = new AndroidLogger();

    @Override
    public boolean onStartJob(JobParameters params) {
        publishNewLearnification();
        return false;
    }

    private void publishNewLearnification() {
        logger.i(LOG_TAG, "Job started");
        LearnificationAppDatabase database = new LearnificationAppDatabase(this);

        LearningItemSupplier learningItemSupplier = new LearningItemSqlTableClient(logger, database);

        List<LearningItem> learningItems = learningItemSupplier.items();
        for (LearningItem learningItem : learningItems) {
            logger.i(LOG_TAG, "using learning item '" + learningItem.toDisplayString() + "'");
        }

        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        LearnificationPublisher learnificationPublisher = new LearnificationPublisher(logger,
                new AndroidNotificationPublisher(logger, NotificationManagerCompat.from(this)),
                new LearnificationFactory(logger, this,
                        new PendingIntentIdGenerator(logger, database),
                        new NotificationIdGenerator(logger, database),
                        new SettingsRepository(logger, fileStorageAdaptor).learnificationTextGenerator(learningItemSupplier))
        );

        learnificationPublisher.publishLearnification();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        logger.i(LOG_TAG, "Job stopped");
        return false;
    }
}
