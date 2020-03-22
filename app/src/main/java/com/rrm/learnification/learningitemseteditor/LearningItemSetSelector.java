package com.rrm.learnification.learningitemseteditor;

import android.widget.Spinner;

class LearningItemSetSelector {
    LearningItemSetSelector(LearningItemSetSelectorView learningItemSetSelectorView, LearningItemSetSelectorAdaptor adapter) {
        Spinner spinner = learningItemSetSelectorView.learningItemSetSelector();
        spinner.setAdapter(adapter);
    }
}
