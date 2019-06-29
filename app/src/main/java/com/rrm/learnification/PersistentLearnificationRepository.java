package com.rrm.learnification;

import java.util.ArrayList;
import java.util.List;

class PersistentLearnificationRepository implements LearnificationRepository {
    private static final String LOG_TAG = "PersistentLearnificationRepository";

    private final AndroidLogger logger;
    private final List<LearningItem> learningItems;

    PersistentLearnificationRepository(AndroidLogger logger, List<LearningItem> learningItems) {
        this.logger = logger;
        this.learningItems = learningItems;
    }

    static LearnificationRepository createInstance(AndroidLogger androidLogger) {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(learningItemTemplate.build("Egypt", "Cairo"));
        learningItems.add(learningItemTemplate.build("Great Britain", "London"));
        learningItems.add(learningItemTemplate.build("Georgia", "Tbilisi"));
        return new PersistentLearnificationRepository(androidLogger, learningItems);
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
