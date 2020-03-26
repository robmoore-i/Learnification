package com.rrm.learnification.publication;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.PendingIntentRequestCodeGenerator;
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
        return publishNewLearnification();
    }

    private boolean publishNewLearnification() {
        logger.v(LOG_TAG, "Job started");
        LearnificationAppDatabase learnificationAppDatabase = new LearnificationAppDatabase(this);

        LearningItemSupplier learningItemSupplier = new LearningItemSqlTableClient(logger, learnificationAppDatabase);

        List<LearningItem> learningItems = learningItemSupplier.items();
        for (LearningItem learningItem : learningItems) {
            logger.v(LOG_TAG, "using learning item '" + learningItem.toDisplayString() + "'");
        }

        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        SettingsRepository settingsRepository = new SettingsRepository(logger, fileStorageAdaptor);
        LearnificationTextGenerator learnificationTextGenerator = settingsRepository.learnificationTextGenerator(learningItemSupplier);

        NotificationIdGenerator notificationIdGenerator = NotificationIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor);
        PendingIntentRequestCodeGenerator pendingIntentRequestCodeGenerator = PendingIntentRequestCodeGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor);
        AndroidNotificationFacade androidNotificationFacade = AndroidNotificationFacade.fromContext(logger, this, notificationIdGenerator, pendingIntentRequestCodeGenerator);
        LearnificationPublisher learnificationPublisher = new LearnificationPublisher(
                logger,
                learnificationTextGenerator,
                androidNotificationFacade
        );

        learnificationPublisher.publishLearnification();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        logger.v(LOG_TAG, "Job stopped");
        return false;
    }
}
