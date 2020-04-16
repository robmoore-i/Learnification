package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemText;

public interface LearningItemRecordStore extends LearningItemSupplier {
    void write(LearningItemText item);

    void delete(LearningItemText item);

    void replace(LearningItemText target, LearningItemText replacement);

    LearningItem applySet(LearningItemText learningItemText);
}
