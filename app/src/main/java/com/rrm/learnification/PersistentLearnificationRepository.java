package com.rrm.learnification;

import java.util.List;

class PersistentLearnificationRepository implements LearnificationRepository {
    private static final String LOG_TAG = "PersistentLearnificationRepository";

    private final AndroidLogger logger;
    private final LearnificationStorage learnificationStorage;
    private final List<LearningItem> learningItems;

    PersistentLearnificationRepository(AndroidLogger logger, LearnificationStorage learnificationStorage) {
        this.logger = logger;
        this.learnificationStorage = learnificationStorage;
        this.learningItems = learnificationStorage.read();
    }

    static PersistentLearnificationRepository loadInstance(AndroidLogger androidLogger, FromFileLearnificationStorage learnificationStorage) {
        return new PersistentLearnificationRepository(androidLogger, learnificationStorage);
    }

    @Override
    public List<LearningItem> learningItems() {
        return learningItems;
    }

    @Override
    public void add(LearningItem learningItem) {
        logger.v(LOG_TAG, "Adding a learning-item '" + learningItem.asSingleString() + "'");
        learningItems.add(learningItem);
        learnificationStorage.write(learningItem);
    }

    @Override
    public void removeAt(int index) {
        logger.v(LOG_TAG, "Removing a learning-item at index " + index);
        learningItems.remove(index);
        learnificationStorage.rewrite(learningItems);
    }
}
