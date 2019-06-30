package com.rrm.learnification;

import java.util.List;

interface LearnificationStorage {
    List<LearningItem> read();

    void write(LearningItem learningItems);
}
