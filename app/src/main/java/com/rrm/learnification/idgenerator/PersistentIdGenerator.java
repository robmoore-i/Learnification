package com.rrm.learnification.idgenerator;

import com.rrm.learnification.idgenerator.serializable.SqlDatabaseIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;

public abstract class PersistentIdGenerator {
    private final IdGenerator idGenerator;

    protected PersistentIdGenerator(AndroidLogger logger, LearnificationAppDatabase database, String idType) {
        idGenerator = new SqlDatabaseIdGenerator(logger, database, idType);
    }

    public int next() {
        return idGenerator.nextId();
    }

    public int last() {
        return idGenerator.lastId();
    }

    public void reset() {
        idGenerator.reset();
    }
}
