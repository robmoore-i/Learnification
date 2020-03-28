package com.rrm.learnification.support;

import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import java.util.List;

public class DatabaseTestWrapper {
    private static final String LOG_TAG = "DatabaseTestWrapper";

    private final AppCompatActivity activity;

    private List<LearningItem> originalLearningItems;
    private LearningItemSqlTableClient learningItemSqlTableClient;
    private AndroidLogger logger;

    public DatabaseTestWrapper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        logger = androidTestObjectFactory.getLogger();
        learningItemSqlTableClient = new LearningItemSqlTableClient(new AndroidLogger(), androidTestObjectFactory.getLearnificationAppDatabase());
        originalLearningItems = learningItemSqlTableClient.items();
        learningItemSqlTableClient.clearEverything();
        logger.i(LOG_TAG, "==== TEST START ====");
    }

    public void afterEach() {
        logger.i(LOG_TAG, "==== TEST FINISH ====");
        learningItemSqlTableClient.clearEverything();
        learningItemSqlTableClient.writeAll(originalLearningItems);
    }
}
