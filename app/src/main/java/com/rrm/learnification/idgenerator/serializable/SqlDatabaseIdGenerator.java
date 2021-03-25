package com.rrm.learnification.idgenerator.serializable;

import com.rrm.learnification.idgenerator.IdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;

public class SqlDatabaseIdGenerator implements IdGenerator {
    private static final String LOG_TAG = "SqlDatabaseIdGenerator";

    private final AndroidLogger logger;
    private final LearnificationAppDatabase database;
    private final String idType;

    public SqlDatabaseIdGenerator(AndroidLogger logger, LearnificationAppDatabase database, String idType) {
        this.logger = logger;
        this.database = database;
        this.idType = idType;

        IdGeneratorSqlTable.storeZeroForIdType(database.getWritableDatabase(), idType);
        logger.i(LOG_TAG, "zeroed the ids for id type '" + idType + "'");
    }

    @Override
    public int nextId() {
        return IdGeneratorSqlTable.getLatestAndIncrement(logger, database.getWritableDatabase(), idType);
    }

    @Override
    public int lastId() {
        return Math.max(IdGeneratorSqlTable.getLatest(logger, database.getReadableDatabase(), idType) - 1, 0);
    }

    @Override
    public void reset() {
        IdGeneratorSqlTable.setToZero(database.getWritableDatabase(), idType);
    }
}
