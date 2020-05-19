package com.rrm.learnification.learnification.publication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.AndroidNotificationPublisher;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.PendingIntentIdGenerator;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.LearningItemSupplier;

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
        LearnificationAppDatabase learnificationAppDatabase = new LearnificationAppDatabase(this);

        LearningItemSupplier learningItemSupplier = new LearningItemSqlTableClient(logger, learnificationAppDatabase);

        List<LearningItem> learningItems = learningItemSupplier.items();
        for (LearningItem learningItem : learningItems) {
            logger.i(LOG_TAG, "using learning item '" + learningItem.toDisplayString() + "'");
        }

        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        SettingsRepository settingsRepository = new SettingsRepository(logger, fileStorageAdaptor);
        LearnificationTextGenerator learnificationTextGenerator = settingsRepository.learnificationTextGenerator(learningItemSupplier);

        NotificationIdGenerator notificationIdGenerator = NotificationIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor);
        PendingIntentIdGenerator pendingIntentRequestCodeGenerator = PendingIntentIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor);
        LearnificationPublisher learnificationPublisher = new LearnificationPublisher(
                logger,
                learnificationTextGenerator,
                new AndroidNotificationFactory(logger, this, pendingIntentRequestCodeGenerator),
                new AndroidNotificationPublisher(logger, NotificationManagerCompat.from(this), notificationIdGenerator)
        );

        learnificationPublisher.publishLearnification();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        logger.i(LOG_TAG, "Job stopped");
        return false;
    }
}
