package com.rrm.learnification;

import java.util.ArrayList;
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

    static PersistentLearnificationRepository loadInstance(AndroidStorage androidStorage, AndroidLogger androidLogger) {
        return new PersistentLearnificationRepository(androidLogger, new FromFileLearnificationStorage(androidLogger, androidStorage));
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
        learnificationStorage.write(learningItem);
    }

    @Override
    public void removeAt(int index) {
    }
}
