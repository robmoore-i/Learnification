package com.rrm.learnification;

import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import java.util.List;

class DatabaseTestWrapper {
    private static final String LOG_TAG = "DatabaseTestWrapper";

    private final AppCompatActivity activity;

    private List<LearningItem> originalLearningItems;
    private LearningItemSqlTableClient learningItemSqlTableClient;
    private AndroidLogger logger;

    DatabaseTestWrapper(AppCompatActivity activity) {
        this.activity = activity;
    }

    void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        logger = androidTestObjectFactory.getLogger();
        learningItemSqlTableClient = new LearningItemSqlTableClient(new AndroidLogger(), androidTestObjectFactory.getLearnificationAppDatabase());
        originalLearningItems = learningItemSqlTableClient.items();
        learningItemSqlTableClient.clearEverything();
        logger.v(LOG_TAG, "==== TEST START ====");
    }

    void afterEach() {
        logger.v(LOG_TAG, "==== TEST FINISH ====");
        learningItemSqlTableClient.clearEverything();
        learningItemSqlTableClient.writeAll(originalLearningItems);
    }
}
