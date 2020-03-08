package com.rrm.learnification.storage;

import android.database.Cursor;

import com.rrm.learnification.common.LearningItem;

import java.util.ArrayList;

public class LearningItemSqlTableClient {
    private final LearnificationAppDatabase learnificationAppDatabase;

    public LearningItemSqlTableClient(LearnificationAppDatabase learnificationAppDatabase) {
        this.learnificationAppDatabase = learnificationAppDatabase;
    }

    public ArrayList<LearningItem> allRecords() {
        Cursor cursor = LearningItemSqlTable.all(learnificationAppDatabase.getReadableDatabase());
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            learningItems.add(LearningItemSqlTable.extract(cursor));
        }
        cursor.close();
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
}
