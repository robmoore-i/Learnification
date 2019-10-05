package com.rrm.learnification.learnification;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.random.JavaRandomiser;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSqlTableInterface;
import com.rrm.learnification.storage.LearningItemStorage;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.storage.SqlLiteLearningItemStorage;

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
        LearningItemStorage learningItemStorage = new SqlLiteLearningItemStorage(new LearnificationAppDatabase(this), new LearningItemSqlTableInterface());
        ItemRepository<LearningItem> itemRepository = new PersistentLearningItemRepository(logger, learningItemStorage);
        List<LearningItem> learningItems = itemRepository.items();
        for (LearningItem learningItem : learningItems) {
            logger.v(LOG_TAG, "using learning item '" + learningItem.asSingleString() + "'");
        }

        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), itemRepository);
        AndroidNotificationFacade androidNotificationFacade = AndroidNotificationFacade.fromContext(logger, this);
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
