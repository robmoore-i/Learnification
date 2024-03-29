package com.rrm.learnification.learningitemstorage;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersistentLearningItemRepository implements LearningItemSupplier {
    private static final String LOG_TAG = "PersistentLearningItemRepository";
    private final AndroidLogger logger;

    private final List<LearningItem> learningItems;
    private final LearningItemRecordStore learningItemRecordStore;
    private final LearningItemTextUpdateBroker itemTextUpdateBroker;

    public PersistentLearningItemRepository(AndroidLogger logger, LearningItemRecordStore learningItemRecordStore,
                                            LearningItemTextUpdateBroker itemTextUpdateBroker) {
        this.logger = logger;
        this.learningItemRecordStore = learningItemRecordStore;
        this.learningItems = learningItemRecordStore.items();
        this.itemTextUpdateBroker = itemTextUpdateBroker;

        for (LearningItem learningItem : learningItems) {
            logger.i(LOG_TAG, "using learning item '" + learningItem.toDisplayString() + "'");
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

    public void add(LearningItemText learningItemText) {
        LearningItem item = learningItemRecordStore.applySet(learningItemText);
        logger.i(LOG_TAG, "adding a learning item '" + learningItemText + "'");
        learningItemRecordStore.write(learningItemText);
        learningItems.add(item);
    }

    public void removeAt(int index) {
        logger.i(LOG_TAG, "removing a learning item at index " + index + " in the view");
        remove(learningItems.get(index));
    }

    private void remove(LearningItem learningItem) {
        logger.i(LOG_TAG, "removing learning item '" + learningItem.toDisplayString() + "'");
        learningItemRecordStore.delete(learningItem.toDisplayString());
        learningItems.remove(learningItem);
    }

    public void replace(LearningItemText targetText, LearningItemText replacementText) {
        LearningItem target = learningItemRecordStore.applySet(targetText);
        LearningItem replacement = learningItemRecordStore.applySet(replacementText);

        itemTextUpdateBroker.sendUpdate(targetText, replacementText);

        learningItemRecordStore.replace(targetText, replacementText);
        learningItems.replaceAll(learningItem -> {
            if (learningItem.equals(target)) return replacement;
            return learningItem;
        });
    }

    public void subscribeToModifications(LearningItemText itemText, LearningItemTextUpdateListener itemUpdateListener) {
        logger.i(LOG_TAG, "assigning change listener to item '" + itemText + "'");
        itemTextUpdateBroker.put(itemText, itemUpdateListener);
    }

    public LearningItem get(LearningItemText displayString) {
        Optional<LearningItem> optional = learningItems.stream().filter(learningItem -> displayString.equals(learningItem.toDisplayString())).findFirst();
        if (!optional.isPresent()) {
            throw new IllegalStateException("No learning item matches the display string '" + displayString.toString() + "'");
        }
        return optional.get();
    }

    public List<LearningItemText> textEntries() {
        return learningItems.stream().map(LearningItem::toDisplayString).collect(Collectors.toList());
    }

    public void refresh() {
        learningItems.clear();
        learningItems.addAll(learningItemRecordStore.items());
    }
}
