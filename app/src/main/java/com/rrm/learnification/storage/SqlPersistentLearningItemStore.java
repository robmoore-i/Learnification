package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.List;

public class SqlPersistentLearningItemStore implements PersistentItemStore<LearningItem> {
    private static final String LOG_TAG = "SqlLiteLearningItemStorage";

    private final AndroidLogger logger;
    private final SqlRecordStore<LearningItem> recordStore;

    public SqlPersistentLearningItemStore(AndroidLogger logger, SqlRecordStore<LearningItem> recordStore) {
        this.logger = logger;
        this.recordStore = recordStore;
    }

    @Override
    public List<LearningItem> read() {
        return recordStore.readAll();
    }

    @Override
    public void write(LearningItem learningItem) {
        logger.v(LOG_TAG, "writing learning item '" + learningItem.asSingleString() + "'");
        recordStore.write(learningItem);
    }

    @Override
    public void remove(LearningItem learningItem) {
        logger.v(LOG_TAG, "removing learning item '" + learningItem.asSingleString() + "'");
        recordStore.delete(learningItem);
    }

    @Override
    public void rewrite(List<LearningItem> learningItems) {
        recordStore.deleteAll();
        recordStore.writeAll(learningItems);
    }

    @Override
    public void replace(LearningItem target, LearningItem replacement) {
        recordStore.replace(target, replacement);
    }
}
