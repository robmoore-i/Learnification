package com.rrm.learnification;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class PersistentLearningItemRepository implements LearningItemRepository {
    private static final String LOG_TAG = "PersistentLearningItemRepository";

    private final AndroidLogger logger;
    private final LearningItemStorage learningItemStorage;
    private final List<LearningItem> learningItems;

    PersistentLearningItemRepository(AndroidLogger logger, LearningItemStorage learningItemStorage) {
        this.logger = logger;
        this.learningItemStorage = learningItemStorage;
        this.learningItems = learningItemStorage.read();

        logger.v(LOG_TAG, "read learning items from storage");
        for (LearningItem learningItem : learningItems) {
            logger.v(LOG_TAG, "using learning item '" + learningItem.asSingleString() + "'");
        }
    }

    @Override
    public List<LearningItem> learningItems() {
        ListIterator<LearningItem> li = learningItems.listIterator(learningItems.size());
        ArrayList<LearningItem> reversed = new ArrayList<>();
        while (li.hasPrevious()) {
            reversed.add(li.previous());
        }
        return reversed;
    }

    @Override
    public void add(LearningItem learningItem) {
        logger.v(LOG_TAG, "adding a learning-item '" + learningItem.asSingleString() + "'");
        learningItems.add(learningItem);
        learningItemStorage.write(learningItem);
    }

    @Override
    public void removeAt(int index) {
        logger.v(LOG_TAG, "removing a learning-item at index " + index);
        learningItems.remove(index);
        learningItemStorage.remove(learningItems, index);
    }
}
