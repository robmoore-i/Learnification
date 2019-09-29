package com.rrm.learnification.common;

import java.util.List;

interface LearningItemStorage {
    List<LearningItem> read();

    void write(LearningItem learningItem);

    void remove(List<LearningItem> learningItems, int index);
}
