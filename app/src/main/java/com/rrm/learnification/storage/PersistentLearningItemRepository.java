package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PersistentLearningItemRepository implements ItemRepository<LearningItem> {
    private static final String LOG_TAG = "PersistentLearningItemRepository";

    private final AndroidLogger logger;
    private final ItemStorage<LearningItem> learningItemStorage;
    private final List<LearningItem> learningItems;

    private final ItemChangeListenerGroup<LearningItem> itemItemChangeListenerGroup;

    public PersistentLearningItemRepository(AndroidLogger logger, ItemStorage<LearningItem> learningItemStorage, ItemChangeListenerGroup<LearningItem> changeListenerGroup) {
        this.logger = logger;
        this.learningItemStorage = learningItemStorage;
        this.learningItems = learningItemStorage.read();
        this.itemItemChangeListenerGroup = changeListenerGroup;

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
        learningItemStorage.write(item);
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
        learningItemStorage.remove(item);
        learningItems.remove(item);
    }

    @Override
    public void replace(LearningItem target, LearningItem replacement) {
        logger.v(LOG_TAG, "replacing '" + target.asSingleString() + "' with '" + replacement.asSingleString() + "'");

        itemItemChangeListenerGroup.handleChange(target, replacement);

        learningItemStorage.replace(target, replacement);
        learningItems.replaceAll(learningItem -> {
            if (learningItem.equals(target)) return replacement;
            return learningItem;
        });
    }

    @Override
    public void subscribeToModifications(LearningItem item, ItemChangeListener<LearningItem> itemChangeListener) {
        logger.v(LOG_TAG, "assigning change listener to item '" + item.asSingleString() + "'");
        itemItemChangeListenerGroup.put(item, itemChangeListener);
    }
}
