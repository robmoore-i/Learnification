package com.rrm.learnification.common;

import java.util.List;

public interface LearningItemRepository {
    List<LearningItem> learningItems();

    void add(LearningItem learningItem);

    void removeAt(int index);
}
