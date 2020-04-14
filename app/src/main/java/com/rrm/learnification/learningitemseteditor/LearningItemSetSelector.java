package com.rrm.learnification.learningitemseteditor;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.LearningItemSetNameChangeListener;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;

class LearningItemSetSelector implements LearningItemSetNameChangeListener {
    private static final String LOG_TAG = "LearningItemSetSelector";

    private final AndroidLogger logger;

    private final LearningItemSetSelectorAdaptor adapter;
    private final Spinner spinner;
    private final LearningItemSetTitle learningItemSetTitle;

    LearningItemSetSelector(AndroidLogger logger, LearningItemSetSelectorView learningItemSetSelectorView, SqlLearningItemSetRecordStore sqlLearningItemSetRecordStore, LearningItemSetSelectorAdaptor adapter, LearningItemSetTitle learningItemSetTitle) {
        this.logger = logger;
        this.adapter = adapter;
        this.spinner = learningItemSetSelectorView.learningItemSetSelector();
        this.learningItemSetTitle = learningItemSetTitle;

        spinner.setAdapter(adapter);
        configureSpinner(spinner);
        sqlLearningItemSetRecordStore.addLearningItemSetRenameListener(this);
    }

    void select(String learningItemSetName) {
        spinner.setSelection(adapter.getPosition(learningItemSetName));
    }

    private void configureSpinner(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOptionText = adapter.getItem(position);
                if (adapter.addNewSetOptionText.equals(selectedOptionText)) {
                    logger.u(LOG_TAG, "created new learning item set");
                    createNewLearningItemSet();
                } else {
                    logger.u(LOG_TAG, "selected learning item set '" + selectedOptionText + "'");
                    spinner.setSelection(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                logger.u(LOG_TAG, "selected nothing");
            }
        });
    }

    private void createNewLearningItemSet() {
        String newLearningItemSet = adapter.createNewLearningItemSet();
        select(newLearningItemSet);
        learningItemSetTitle.setNewTitle(newLearningItemSet);
    }

    @Override
    public void onLearningItemSetNameChange(String target, String replacement) {
        adapter.renameLearningItemSet(target, replacement);
        select(replacement);
    }
}
