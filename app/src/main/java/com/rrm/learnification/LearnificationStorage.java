package com.rrm.learnification;

import java.util.List;

interface LearnificationStorage {
    List<LearningItem> read();

    void write(LearningItem learningItem);

    void remove(List<LearningItem> learningItems, int index);
}
