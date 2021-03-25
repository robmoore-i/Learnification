package com.rrm.learnification.jobs;

import com.rrm.learnification.idgenerator.PersistentIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;

public class JobIdGenerator extends PersistentIdGenerator {
    private static final String ID_TYPE = "jobs";

    public JobIdGenerator(AndroidLogger logger, LearnificationAppDatabase database) {
        super(logger, database, ID_TYPE);
    }
}