package com.rrm.learnification.learningitemseteditor.learningitemsetselector;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.rrm.learnification.learningitemstorage.LearningItemSetNameChangeListener;
import com.rrm.learnification.learningitemstorage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.logger.AndroidLogger;

public class LearningItemSetSelector implements LearningItemSetNameChangeListener {
    private static final String LOG_TAG = "LearningItemSetSelector";

    private final AndroidLogger logger;

    private final Spinner spinner;
    private final LearningItemSetSelectorAdaptor adapter;
    private final LearningItemSetTitle learningItemSetTitle;
    private final SqlLearningItemSetRecordStore recordStore;

    private LearningItemSetChangeListener setChangeListener;

    public LearningItemSetSelector(AndroidLogger logger, LearningItemSetSelectorView learningItemSetSelectorView, LearningItemSetSelectorAdaptor adapter,
                                   LearningItemSetTitle learningItemSetTitle, SqlLearningItemSetRecordStore recordStore) {
        this.logger = logger;
        this.adapter = adapter;
        this.spinner = learningItemSetSelectorView.learningItemSetSelector();
        this.learningItemSetTitle = learningItemSetTitle;
        this.recordStore = recordStore;

        configureSpinner(spinner);
        recordStore.addLearningItemSetRenameListener(this);
    }

    public void select(String learningItemSetName) {
        recordStore.useSet(learningItemSetName);
        setChangeListener.refresh();
    }

    private void configureSpinner(Spinner spinner) {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOptionText = adapter.getItem(position);
                if (adapter.addNewSetOptionText.equals(selectedOptionText)) {
                    logger.u(LOG_TAG, "created new learning item set");
                    String newLearningItemSetName = adapter.createNewLearningItemSet();
                    learningItemSetTitle.setNewTitle(newLearningItemSetName);
                    select(newLearningItemSetName);
                } else {
                    logger.u(LOG_TAG, "selected learning item set '" + selectedOptionText + "'");
                    learningItemSetTitle.set(selectedOptionText);
                    select(selectedOptionText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                logger.u(LOG_TAG, "selected nothing");
            }
        });
    }

    @Override
    public void onLearningItemSetNameChange(String target, String replacement) {
        adapter.renameLearningItemSet(target, replacement);
        spinner.setSelection(adapter.getPosition(replacement));
    }

    public void registerForSetChanges(LearningItemSetChangeListener setChangeListener) {
        this.setChangeListener = setChangeListener;
    }
}
