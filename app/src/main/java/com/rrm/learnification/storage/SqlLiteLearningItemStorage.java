package com.rrm.learnification.storage;

import android.database.sqlite.SQLiteDatabase;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearningItem;

import java.util.List;

public class SqlLiteLearningItemStorage implements LearningItemStorage {
    private static final String LOG_TAG = "SqlLiteLearningItemStorage";

    private final AndroidLogger logger;
    private final LearnificationAppDatabase database;
    private final SqlTableInterface<LearningItem> tableInterface;

    public SqlLiteLearningItemStorage(AndroidLogger logger, LearnificationAppDatabase database, SqlTableInterface<LearningItem> tableInterface) {
        this.logger = logger;
        this.database = database;
        this.tableInterface = tableInterface;
    }

    @Override
    public List<LearningItem> read() {
        return tableInterface.readAll(database.getReadableDatabase());
    }

    @Override
    public void write(LearningItem learningItem) {
        logger.v(LOG_TAG, "writing learning item '" + learningItem.asSingleString() + "'");
        tableInterface.write(database.getWritableDatabase(), learningItem);
    }

    @Override
    public void remove(LearningItem learningItem) {
        logger.v(LOG_TAG, "removing learning item '" + learningItem.asSingleString() + "'");
        tableInterface.delete(database.getWritableDatabase(), learningItem);
    }

    @Override
    public void rewrite(List<LearningItem> learningItems) {
        SQLiteDatabase writableDatabase = database.getWritableDatabase();
        tableInterface.deleteAll(writableDatabase);
        tableInterface.writeAll(writableDatabase, learningItems);
    }
}
