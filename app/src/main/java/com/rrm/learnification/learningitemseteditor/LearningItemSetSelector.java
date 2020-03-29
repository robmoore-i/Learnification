package com.rrm.learnification.learningitemseteditor;

import android.widget.Spinner;

import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;

class LearningItemSetSelector {
    private final LearningItemSetSelectorAdaptor adapter;
    private final Spinner spinner;

    LearningItemSetSelector(LearningItemSetSelectorView learningItemSetSelectorView, SqlLearningItemSetRecordStore sqlLearningItemSetRecordStore, LearningItemSetSelectorAdaptor adapter) {
        this.adapter = adapter;
        spinner = learningItemSetSelectorView.learningItemSetSelector();
        spinner.setAdapter(adapter);
        sqlLearningItemSetRecordStore.addLearningItemSetRenameListener(adapter);
    }

    void select(String learningItemSetName) {
        spinner.setSelection(adapter.getPosition(learningItemSetName));
    }
}
