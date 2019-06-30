package com.rrm.learnification;

import java.util.List;

interface LearnificationRepository {
    List<LearningItem> learningItems();

    void add(LearningItem learningItem);

    void removeAt(int index);
}
