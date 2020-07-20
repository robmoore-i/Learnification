package com.rrm.learnification.support;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learnificationresultstorage.LearnificationResult;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultSqlTableClient;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.learningitemstorage.LearningItemSqlTableClient;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import java.util.List;

public class GuiTestWrapper {
    private static final String LOG_TAG = "GuiTestWrapper";

    private final AndroidLogger logger;
    private final LearningItemSetEditorActivity activity;
    private final LearningItemSqlTableClient learningItemSqlTableClient;
    private final LearnificationResultSqlTableClient learnificationResultSqlTableClient;

    private List<LearningItem> originalLearningItems;
    private List<LearnificationResult> originalLearnificationResults;

    public GuiTestWrapper(LearningItemSetEditorActivity activity) {
        this.activity = activity;
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        logger = androidTestObjectFactory.getLogger();
        learningItemSqlTableClient = new LearningItemSqlTableClient(new AndroidLogger(), androidTestObjectFactory.getLearnificationAppDatabase());
        learnificationResultSqlTableClient = new LearnificationResultSqlTableClient(androidTestObjectFactory.getLearnificationAppDatabase());
    }

    public void beforeEach() {
        originalLearningItems = learningItemSqlTableClient.items();
        originalLearnificationResults = learnificationResultSqlTableClient.readAll();
        learningItemSqlTableClient.clearEverything();
        learnificationResultSqlTableClient.deleteAll();
        UserSimulation.refreshLearningItemList();
        logger.i(LOG_TAG, "==== TEST START ====");
    }

    public void afterEach() {
        logger.i(LOG_TAG, "==== TEST FINISH ====");
        learningItemSqlTableClient.clearEverything();
        learnificationResultSqlTableClient.deleteAll();
        learningItemSqlTableClient.writeAll(originalLearningItems);
        learnificationResultSqlTableClient.writeAll(originalLearnificationResults);
        UserSimulation.clearNotifications(activity);
        UserSimulation.pressHome();
    }
}
