package com.rrm.learnification;

import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.PersistentItemStore;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import java.util.List;

class DatabaseTestWrapper {
    private final AppCompatActivity activity;

    private PersistentItemStore<LearningItem> learningPersistentItemStore;
    private List<LearningItem> originalLearningItems;

    DatabaseTestWrapper(AppCompatActivity activity) {
        this.activity = activity;
    }

    void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        LearningItemSqlTableClient learningItemSqlTableClient = new LearningItemSqlTableClient(androidTestObjectFactory.getLearnificationAppDatabase());

        learningPersistentItemStore = androidTestObjectFactory.getLearningItemStorage();
        originalLearningItems = learningItemSqlTableClient.items();
        learningItemSqlTableClient.clearEverything();
    }

    void afterEach() {
        learningPersistentItemStore.rewrite(originalLearningItems);
    }
}
