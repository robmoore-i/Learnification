package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PersistentLearningItemRepository implements ItemRepository<LearningItem> {
    private static final String LOG_TAG = "PersistentLearningItemRepository";

    private final AndroidLogger logger;
    private final SqlLearningItemSetRecordStore learningItemStore;
    private final List<LearningItem> learningItems;

    private final ItemUpdateBroker<LearningItem> itemUpdateBroker;

    public PersistentLearningItemRepository(AndroidLogger logger, SqlLearningItemSetRecordStore learningItemStore, ItemUpdateBroker<LearningItem> itemUpdateBroker) {
        this.logger = logger;
        this.learningItemStore = learningItemStore;
        this.learningItems = learningItemStore.readAll();
        this.itemUpdateBroker = itemUpdateBroker;

        for (LearningItem learningItem : learningItems) {
            logger.v(LOG_TAG, "using learning item '" + learningItem.asSingleString() + "'");
        }
    }

    @Override
    public List<LearningItem> items() {
        ListIterator<LearningItem> li = learningItems.listIterator(learningItems.size());
        ArrayList<LearningItem> reversed = new ArrayList<>();
        while (li.hasPrevious()) {
            reversed.add(li.previous());
        }
        return reversed;
    }

    @Override
    public List<LearningItem> itemsOrThrowIfEmpty() {
        List<LearningItem> learningItems = items();
        if (learningItems.isEmpty()) {
            throw new IllegalStateException("there are no learning items");
        }
        return learningItems;
    }

    @Override
    public void add(LearningItem item) {
        logger.v(LOG_TAG, "adding a learning item '" + item.asSingleString() + "'");
        learningItemStore.write(item);
        learningItems.add(item);
    }

    @Override
    public void removeAt(int index) {
        int reversedIndex = learningItems.size() - index - 1;
        logger.v(LOG_TAG, "removing a learning item at index " + index + " in the view, which corresponds to index " + reversedIndex + " in storage");
        remove(learningItems.get(reversedIndex));
    }

    @Override
    public void remove(LearningItem item) {
        logger.v(LOG_TAG, "removing learning item '" + item.asSingleString() + "'");
        learningItemStore.delete(item);
        learningItems.remove(item);
    }

    @Override
    public void replace(LearningItem target, LearningItem replacement) {
        logger.v(LOG_TAG, "replacing '" + target.asSingleString() + "' with '" + replacement.asSingleString() + "'");

        itemUpdateBroker.sendUpdate(target, replacement);

        learningItemStore.replace(target, replacement);
        learningItems.replaceAll(learningItem -> {
            if (learningItem.equals(target)) return replacement;
            return learningItem;
        });
    }

    @Override
    public void subscribeToModifications(LearningItem item, ItemUpdateListener<LearningItem> itemUpdateListener) {
        logger.v(LOG_TAG, "assigning change listener to item '" + item.asSingleString() + "'");
        itemUpdateBroker.put(item, itemUpdateListener);
    }
}
