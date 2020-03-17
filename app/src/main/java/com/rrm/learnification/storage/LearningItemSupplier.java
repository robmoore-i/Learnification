package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

import java.util.List;

public interface LearningItemSupplier {
    List<LearningItem> items();

    List<LearningItem> itemsOrThrowIfEmpty();
}
