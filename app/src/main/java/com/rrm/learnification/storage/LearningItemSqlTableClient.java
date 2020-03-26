package com.rrm.learnification.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;
import java.util.List;

public class LearningItemSqlTableClient implements LearningItemSupplier {
    private static final String LOG_TAG = "LearningItemSqlTableClient";
    private final AndroidLogger logger;

    private final LearnificationAppDatabase learnificationAppDatabase;

    public LearningItemSqlTableClient(AndroidLogger logger, LearnificationAppDatabase learnificationAppDatabase) {
        this.logger = logger;
        this.learnificationAppDatabase = learnificationAppDatabase;
    }

    @Override
    public List<LearningItem> items() {
        Cursor cursor = LearningItemSqlTable.all(learnificationAppDatabase.getReadableDatabase());
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            learningItems.add(LearningItemSqlTable.extract(cursor));
        }
        cursor.close();
        return learningItems;
    }

    @Override
    public List<LearningItem> itemsOrThrowIfEmpty() {
        List<LearningItem> learningItems = items();
        if (learningItems.isEmpty()) {
            throw new IllegalStateException("there are no learning items");
        }
        return learningItems;
    }

    public void clearEverything() {
        logger.v(LOG_TAG, "clearing everything");
        LearningItemSqlTable.deleteAll(learnificationAppDatabase.getWritableDatabase());
    }

    public int numberOfDistinctLearningItemSets() {
        return orderedLearningItemSetNames().size();
    }

    public String mostPopulousLearningItemSetName() {
        return LearningItemSqlTable.mostPopulousLearningItemSetName(learnificationAppDatabase.getReadableDatabase());
    }

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
        logger.v(LOG_TAG, "wrote learning items '" + items.toString() + "'");
    }

    public void write(LearningItem item) {
        learnificationAppDatabase.getWritableDatabase().insert(LearningItemSqlTable.TABLE_NAME, null, LearningItemSqlTable.from(item));
        logger.v(LOG_TAG, "wrote learning item " + item.toString());
    }

    public void deleteAll(List<LearningItem> learningItems) {
        for (LearningItem learningItem : learningItems) {
            LearningItemSqlTable.delete(learnificationAppDatabase.getWritableDatabase(), learningItem);
        }
        logger.v(LOG_TAG, "deleted learning items '" + learningItems.toString() + "'");
    }

    public List<String> orderedLearningItemSetNames() {
        Cursor cursor = LearningItemSqlTable.learningItemSetNamesOrderedBySetSize(learnificationAppDatabase.getReadableDatabase());
        ArrayList<String> learningItemSetNames = new ArrayList<>();
        while (cursor.moveToNext()) {
            learningItemSetNames.add(LearningItemSqlTable.learningItemSetNameFrom(cursor));
        }
        cursor.close();
        if (learningItemSetNames.size() == 0) learningItemSetNames.add("default");
        logger.v(LOG_TAG, "ordered learning item set names '" + learningItemSetNames.toString() + "'");
        return learningItemSetNames;
    }
}
