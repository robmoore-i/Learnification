package com.rrm.learnification;

import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import java.util.List;

class DatabaseTestWrapper {
    private final AppCompatActivity activity;

    private SqlLearningItemSetRecordStore learningPersistentItemStore;
    private List<LearningItem> originalLearningItems;

    DatabaseTestWrapper(AppCompatActivity activity) {
        this.activity = activity;
    }

    void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        LearningItemSqlTableClient learningItemSqlTableClient = new LearningItemSqlTableClient(androidTestObjectFactory.getLearnificationAppDatabase());

        learningPersistentItemStore = androidTestObjectFactory.getDefaultSqlLearningItemSetRecordStore();
        originalLearningItems = learningItemSqlTableClient.items();
        learningItemSqlTableClient.clearEverything();
    }

    void afterEach() {
        learningPersistentItemStore.deleteAll();
        learningPersistentItemStore.writeAll(originalLearningItems);
    }
}
