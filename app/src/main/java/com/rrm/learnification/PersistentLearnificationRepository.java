package com.rrm.learnification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class PersistentLearnificationRepository implements LearnificationRepository {
    private static final String LOG_TAG = "PersistentLearnificationRepository";

    private final AndroidLogger logger;
    private final List<LearningItem> learningItems;

    PersistentLearnificationRepository(AndroidLogger logger, LearnificationProvider learnificationProvider) {
        this.logger = logger;
        this.learningItems = learnificationProvider.get();
    }

    static PersistentLearnificationRepository loadInstance(File filesDir, AndroidLogger androidLogger) {
        return new PersistentLearnificationRepository(androidLogger, new FromFileLearnificationProvider(androidLogger, filesDir));
    }

    @Override
    public List<LearningItem> learningItems() {
        return learningItems;
    }

    @Override
    public List<String> learningItemsAsStringList() {
        List<LearningItem> learningItems = learningItems();

        ArrayList<String> stringifiedItems = new ArrayList<>();
        for (LearningItem learningItem : learningItems) {
            stringifiedItems.add(learningItem.asSingleString());
        }

        return stringifiedItems;
    }

    @Override
    public void add(LearningItem learningItem) {
        logger.v(LOG_TAG, "Adding a learning-item '" + learningItem.asSingleString() + "'");
        learningItems.add(learningItem);
    }
}
