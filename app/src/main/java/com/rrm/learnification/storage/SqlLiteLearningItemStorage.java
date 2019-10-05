package com.rrm.learnification.storage;

import android.database.sqlite.SQLiteDatabase;

import com.rrm.learnification.common.LearningItem;

import java.util.List;

public class SqlLiteLearningItemStorage implements LearningItemStorage {
    private final LearnificationAppDatabase database;
    private final SqlTableInterface<LearningItem> tableInterface;

    public SqlLiteLearningItemStorage(LearnificationAppDatabase database, SqlTableInterface<LearningItem> tableInterface) {
        this.database = database;
        this.tableInterface = tableInterface;
    }

    @Override
    public List<LearningItem> read() {
        return tableInterface.readAll(database.getReadableDatabase());
    }

    @Override
    public void write(LearningItem learningItem) {
        tableInterface.write(database.getWritableDatabase(), learningItem);
    }

    @Override
    public void remove(List<LearningItem> learningItems, int index) {
        tableInterface.delete(database.getWritableDatabase(), learningItems.get(index));
    }

    @Override
    public void rewrite(List<LearningItem> learningItems) {
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        tableInterface.deleteAll(writableDatabase);
        tableInterface.writeAll(writableDatabase, learningItems);
    }
}
