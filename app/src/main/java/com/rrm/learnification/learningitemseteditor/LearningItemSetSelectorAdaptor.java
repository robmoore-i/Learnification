package com.rrm.learnification.learningitemseteditor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.LearningItemSetNameChangeListener;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;

import java.util.List;

public class LearningItemSetSelectorAdaptor extends ArrayAdapter<String> implements LearningItemSetNameChangeListener {
    private static final String LOG_TAG = "LearningItemSetSelectorAdaptor";

    private final AndroidLogger logger;

    private final LearningItemSqlTableClient learningItemSqlTableClient;
    private final List<String> learningItemSetNames;

    LearningItemSetSelectorAdaptor(AndroidLogger logger, Context context, SqlLearningItemSetRecordStore sqlLearningItemSetRecordStore, LearningItemSqlTableClient learningItemSqlTableClient, List<String> learningItemSetNames) {
        super(context, R.layout.learning_item_set_selector_entry, R.id.learning_item_set_selector_text_entry, learningItemSetNames);
        this.learningItemSetNames = learningItemSetNames;
        logger.v(LOG_TAG, "initial learning-item-set-names are " + learningItemSetNames.toString());
        this.logger = logger;
        this.learningItemSqlTableClient = learningItemSqlTableClient;
        sqlLearningItemSetRecordStore.addRenameListener(this);
    }

    @Override
    public int getCount() {
        int count = learningItemSqlTableClient.numberOfDistinctLearningItemSets();
        logger.v(LOG_TAG, "count for learning-item-set-selector is " + count);
        return count;
    }

    @Override
    public void onLearningItemSetNameChange(String target, String replacement) {
        logger.v(LOG_TAG, "replacing '" + target + "' with '" + replacement + "'");
        this.remove(target);
        this.add(replacement);
        logger.v(LOG_TAG, "spinner list is now " + learningItemSetNames.toString());
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
