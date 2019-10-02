package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

import java.util.List;

public interface LearningItemRepository {
    List<LearningItem> learningItems();

    void add(LearningItem learningItem);

    void removeAt(int index);
}
