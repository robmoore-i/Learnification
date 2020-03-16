package com.rrm.learnification.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.List;

public class SqlLearningItemSetRecordStore implements SqlRecordStore<LearningItem> {
    private static final String LOG_TAG = "LearningItemSetSqlRecordStore";
    private final AndroidLogger logger;

    private final LearnificationAppDatabase learnificationAppDatabase;

    private String learningItemSetName;

    public SqlLearningItemSetRecordStore(AndroidLogger logger, LearnificationAppDatabase learnificationAppDatabase, String learningItemSetName) {
        this.logger = logger;
        this.learnificationAppDatabase = learnificationAppDatabase;
        this.learningItemSetName = learningItemSetName;
    }

    @Override
    public List<LearningItem> readAll() {
        Cursor cursor = LearningItemSqlTable.all(learnificationAppDatabase.getReadableDatabase(), learningItemSetName);
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            learningItems.add(LearningItemSqlTable.extract(cursor));
        }
        cursor.close();
        return learningItems;
    }

    @Override
    public void deleteAll() {
        LearningItemSqlTable.deleteAll(learnificationAppDatabase.getWritableDatabase(), learningItemSetName);
    }

    @Override
    public void writeAll(List<LearningItem> items) {
        SQLiteDatabase writableDatabase = learnificationAppDatabase.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            for (LearningItem item : items) {
                write(item);
            }
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @Override
    public void write(LearningItem item) {
        learnificationAppDatabase.getWritableDatabase().insert(LearningItemSqlTable.TABLE_NAME, null, LearningItemSqlTable.from(learningItemSetName, item));
    }

    @Override
    public void delete(LearningItem item) {
        LearningItemSqlTable.delete(learnificationAppDatabase.getWritableDatabase(), learningItemSetName, item);
    }

    @Override
    public void replace(LearningItem target, LearningItem replacement) {
        LearningItemSqlTable.replace(learnificationAppDatabase.getWritableDatabase(), learningItemSetName, target, replacement);
    }

    public void renameSet(String newLearningItemSetName) {
        logger.v(LOG_TAG, "renaming learning item set from '" + learningItemSetName + "' to '" + newLearningItemSetName + "'");
        LearningItemSqlTable.updateSetName(learnificationAppDatabase.getWritableDatabase(), learningItemSetName, newLearningItemSetName);
        learningItemSetName = newLearningItemSetName;
    }
}
