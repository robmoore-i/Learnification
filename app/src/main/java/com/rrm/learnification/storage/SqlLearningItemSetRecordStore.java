package com.rrm.learnification.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.List;

public class SqlLearningItemSetRecordStore {
    private static final String LOG_TAG = "LearningItemSetSqlRecordStore";
    private final AndroidLogger logger;

    private final LearnificationAppDatabase learnificationAppDatabase;

    private String learningItemSetName;

    public SqlLearningItemSetRecordStore(AndroidLogger logger, LearnificationAppDatabase learnificationAppDatabase, String learningItemSetName) {
        this.logger = logger;
        this.learnificationAppDatabase = learnificationAppDatabase;
        this.learningItemSetName = learningItemSetName;
    }

    public List<LearningItem> readAll() {
        Cursor cursor = LearningItemSqlTable.all(learnificationAppDatabase.getReadableDatabase(), learningItemSetName);
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            learningItems.add(LearningItemSqlTable.extract(cursor));
        }
        cursor.close();
        return learningItems;
    }

    public void deleteAll() {
        LearningItemSqlTable.deleteAll(learnificationAppDatabase.getWritableDatabase(), learningItemSetName);
    }

    public void writeAll(List<LearningItemText> items) {
        SQLiteDatabase writableDatabase = learnificationAppDatabase.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            for (LearningItemText item : items) {
                write(item);
            }
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public void write(LearningItemText item) {
        learnificationAppDatabase.getWritableDatabase().insert(LearningItemSqlTable.TABLE_NAME, null, LearningItemSqlTable.from(item.withSet(learningItemSetName)));
    }

    public void delete(LearningItemText item) {
        logger.v(LOG_TAG, "deleting record '" + item + "'");
        LearningItemSqlTable.delete(learnificationAppDatabase.getWritableDatabase(), item.withSet(learningItemSetName));
    }

    public void replace(LearningItemText target, LearningItemText replacement) {
        LearningItemSqlTable.replace(learnificationAppDatabase.getWritableDatabase(), target.withSet(learningItemSetName), replacement.withSet(learningItemSetName));
    }

    public void renameSet(String newLearningItemSetName) {
        logger.v(LOG_TAG, "renaming learning item set from '" + learningItemSetName + "' to '" + newLearningItemSetName + "'");
        LearningItemSqlTable.updateSetName(learnificationAppDatabase.getWritableDatabase(), learningItemSetName, newLearningItemSetName);
        learningItemSetName = newLearningItemSetName;
    }

    LearningItem applySet(LearningItemText learningItemText) {
        return learningItemText.withSet(learningItemSetName);
    }
}
