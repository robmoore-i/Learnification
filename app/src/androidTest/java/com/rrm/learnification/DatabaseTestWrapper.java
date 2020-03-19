package com.rrm.learnification;

import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import java.util.List;

class DatabaseTestWrapper {
    private final AppCompatActivity activity;

    private List<LearningItem> originalLearningItems;
    private LearningItemSqlTableClient learningItemSqlTableClient;

    DatabaseTestWrapper(AppCompatActivity activity) {
        this.activity = activity;
    }

    void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        learningItemSqlTableClient = new LearningItemSqlTableClient(androidTestObjectFactory.getLearnificationAppDatabase());
        originalLearningItems = learningItemSqlTableClient.items();
        learningItemSqlTableClient.clearEverything();
    }

    void afterEach() {
        learningItemSqlTableClient.clearEverything();
        learningItemSqlTableClient.writeAll(originalLearningItems);
    }
}
