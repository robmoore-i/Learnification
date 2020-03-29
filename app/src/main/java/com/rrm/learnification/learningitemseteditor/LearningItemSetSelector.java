package com.rrm.learnification.learningitemseteditor;

import android.widget.Spinner;

import com.rrm.learnification.storage.LearningItemSetNameChangeListener;

class LearningItemSetSelector implements LearningItemSetNameChangeListener {
    private final LearningItemSetSelectorAdaptor adapter;

    LearningItemSetSelector(LearningItemSetSelectorView learningItemSetSelectorView, LearningItemSetSelectorAdaptor adapter) {
        this.adapter = adapter;
        Spinner spinner = learningItemSetSelectorView.learningItemSetSelector();
        spinner.setAdapter(adapter);
    }

    @Override
    public void onLearningItemSetNameChange(String target, String replacement) {
        adapter.onLearningItemSetNameChange(target, replacement);
    }
}
