package com.rrm.learnification.textlist;

import com.rrm.learnification.learningitemseteditor.LearningItemListViewAdaptor;

public interface OnSwipeCommand {
    void onSwipe(LearningItemListViewAdaptor adapter, int index);
}
