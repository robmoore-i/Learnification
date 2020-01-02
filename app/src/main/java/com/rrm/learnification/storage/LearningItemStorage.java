package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

import java.util.List;

public interface LearningItemStorage {
    List<LearningItem> read();

    void write(LearningItem learningItem);

    void remove(LearningItem learningItem);

    void rewrite(List<LearningItem> learningItems);
}
