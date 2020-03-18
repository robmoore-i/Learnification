package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

public interface LearningItemUpdateListener {
    void onItemChange(LearningItem updatedItem);
}
