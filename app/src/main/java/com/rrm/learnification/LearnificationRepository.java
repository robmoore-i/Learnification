package com.rrm.learnification;

import java.util.List;

interface LearnificationRepository {
    List<LearningItem> learningItems();

    List<String> learningItemsAsStringList();
}
