package com.rrm.learnification.learningitemseteditor.learningitemremove;

import com.rrm.learnification.learningitemseteditor.learningitemlist.LearningItemListViewAdaptor;

public interface OnSwipeCommand {
    void onSwipe(LearningItemListViewAdaptor adapter, int index);
}
