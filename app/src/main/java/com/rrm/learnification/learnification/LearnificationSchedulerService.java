package com.rrm.learnification.learnification;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.rrm.learnification.common.AndroidInternalStorageAdaptor;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.FileStorageAdaptor;
import com.rrm.learnification.common.FromFileLearningItemStorage;
import com.rrm.learnification.common.JavaRandomiser;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemRepository;
import com.rrm.learnification.common.PersistentLearningItemRepository;

import java.util.List;

public class LearnificationSchedulerService extends JobService {
    private static final String LOG_TAG = "LearnificationSchedulerService";

    private final AndroidLogger logger = new AndroidLogger();

    @Override
    public boolean onStartJob(JobParameters params) {
        logger.v(LOG_TAG, "Job started");
        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        LearningItemRepository learningItemRepository = new PersistentLearningItemRepository(logger, new FromFileLearningItemStorage(logger, fileStorageAdaptor));

        List<LearningItem> learningItems = learningItemRepository.learningItems();
        for (LearningItem learningItem : learningItems) {
            logger.v(LOG_TAG, "using learning item '" + learningItem.asSingleString() + "'");
        }

        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(new JavaRandomiser(), learningItemRepository);
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
