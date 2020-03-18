package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class PersistentLearningItemRepository implements LearningItemSupplier {
    private static final String LOG_TAG = "PersistentLearningItemRepository";

    private final AndroidLogger logger;
    private final SqlLearningItemSetRecordStore learningItemStore;
    private final List<LearningItem> learningItems;

    private final LearningItemUpdateBroker itemUpdateBroker;

    public PersistentLearningItemRepository(AndroidLogger logger, SqlLearningItemSetRecordStore learningItemStore, LearningItemUpdateBroker itemUpdateBroker) {
        this.logger = logger;
        this.learningItemStore = learningItemStore;
        this.learningItems = learningItemStore.readAll();
        this.itemUpdateBroker = itemUpdateBroker;

        for (LearningItem learningItem : learningItems) {
            logger.v(LOG_TAG, "using learning item '" + learningItem.toDisplayString() + "'");
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

    public void add(LearningItem item) {
        logger.v(LOG_TAG, "adding a learning item '" + item.toDisplayString() + "'");
        learningItemStore.write(item);
        learningItems.add(item);
    }

    public void removeAt(int index) {
        int reversedIndex = learningItems.size() - index - 1;
        logger.v(LOG_TAG, "removing a learning item at index " + index + " in the view, which corresponds to index " + reversedIndex + " in storage");
        remove(learningItems.get(reversedIndex));
    }

    public void remove(LearningItem item) {
        logger.v(LOG_TAG, "removing learning item '" + item.toDisplayString() + "'");
        learningItemStore.delete(item);
        learningItems.remove(item);
    }

    public void replace(LearningItem target, Function<String, LearningItem> partialLearningItem) {
        LearningItem replacement = learningItemStore.applySet(partialLearningItem);

        itemUpdateBroker.sendUpdate(target, replacement);

        learningItemStore.replace(target, replacement);
        learningItems.replaceAll(learningItem -> {
            if (learningItem.equals(target)) return replacement;
            return learningItem;
        });
    }

    public void subscribeToModifications(LearningItem item, LearningItemUpdateListener itemUpdateListener) {
        logger.v(LOG_TAG, "assigning change listener to item '" + item.toDisplayString() + "'");
        itemUpdateBroker.put(item, itemUpdateListener);
    }

    public LearningItem get(Predicate<LearningItem> learningItemPredicate) {
        Optional<LearningItem> optional = learningItems.stream().filter(learningItemPredicate).findFirst();
        if (!optional.isPresent()) {
            throw new IllegalStateException("No learning item matches the predicate '" + learningItemPredicate + "'");
        }
        return optional.get();
    }
}
