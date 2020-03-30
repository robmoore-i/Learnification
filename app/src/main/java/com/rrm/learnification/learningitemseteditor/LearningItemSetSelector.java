package com.rrm.learnification.learningitemseteditor;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;

class LearningItemSetSelector {
    private final LearningItemSetSelectorAdaptor adapter;
    private final Spinner spinner;

    LearningItemSetSelector(LearningItemSetSelectorView learningItemSetSelectorView, SqlLearningItemSetRecordStore sqlLearningItemSetRecordStore, LearningItemSetSelectorAdaptor adapter) {
        this.adapter = adapter;
        spinner = learningItemSetSelectorView.learningItemSetSelector();
        spinner.setAdapter(adapter);
        configureSpinner(spinner);
        sqlLearningItemSetRecordStore.addLearningItemSetRenameListener(adapter);
    }

    void select(String learningItemSetName) {
        spinner.setSelection(adapter.getPosition(learningItemSetName));
    }

    private void configureSpinner(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
