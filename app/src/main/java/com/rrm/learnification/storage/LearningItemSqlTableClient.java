package com.rrm.learnification.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rrm.learnification.common.LearningItem;

import java.util.ArrayList;
import java.util.List;

public class LearningItemSqlTableClient implements LearningItemSupplier {
    private final LearnificationAppDatabase learnificationAppDatabase;

    public LearningItemSqlTableClient(LearnificationAppDatabase learnificationAppDatabase) {
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
        LearningItemSqlTable.deleteAll(learnificationAppDatabase.getWritableDatabase());
    }

    public int numberOfDistinctLearningItemSets() {
        return LearningItemSqlTable.numberOfDistinctLearningItemSets(learnificationAppDatabase.getReadableDatabase());
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
    }

    public void write(LearningItem item) {
        learnificationAppDatabase.getWritableDatabase().insert(LearningItemSqlTable.TABLE_NAME, null, LearningItemSqlTable.from(item));
    }
}
