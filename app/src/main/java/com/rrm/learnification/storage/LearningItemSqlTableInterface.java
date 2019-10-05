package com.rrm.learnification.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rrm.learnification.common.LearningItem;

import java.util.ArrayList;
import java.util.List;

public class LearningItemSqlTableInterface implements SqlTableInterface<LearningItem> {
    @Override
    public List<LearningItem> readAll(SQLiteDatabase readableDatabase) {
        Cursor cursor = LearningItemSqlTable.all(readableDatabase);
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            learningItems.add(LearningItemSqlTable.extract(cursor));
        }
        cursor.close();
        return learningItems;
    }

    @Override
    public void deleteAll(SQLiteDatabase writableDatabase) {
        writableDatabase.delete(LearningItemSqlTable.TABLE_NAME, null, null);
    }

    @Override
    public void writeAll(SQLiteDatabase writableDatabase, List<LearningItem> items) {
        writableDatabase.beginTransaction();
        try {
            for (LearningItem item : items) {
                write(writableDatabase, item);
            }
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @Override
    public void write(SQLiteDatabase writableDatabase, LearningItem item) {
        writableDatabase.insert(LearningItemSqlTable.TABLE_NAME, null, LearningItemSqlTable.from(item));
    }

    @Override
    public void delete(SQLiteDatabase writableDatabase, LearningItem item) {
        LearningItemSqlTable.delete(writableDatabase, item);
    }
}
