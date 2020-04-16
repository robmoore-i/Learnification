package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

import java.util.List;

public interface LearningItemSupplier {
    List<LearningItem> items();

    default List<LearningItem> itemsOrThrowIfEmpty() {
        List<LearningItem> learningItems = items();
        if (learningItems.isEmpty()) {
            throw new IllegalStateException("there are no learning items");
        }
        return learningItems;
    }
}
