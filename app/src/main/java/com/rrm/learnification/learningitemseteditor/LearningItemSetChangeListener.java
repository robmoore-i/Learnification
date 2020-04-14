package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItem;

import java.util.List;

interface LearningItemSetChangeListener {
    void refresh(List<LearningItem> newLearningItems);
}
