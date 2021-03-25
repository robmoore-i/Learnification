package com.rrm.learnification.notification;

import com.rrm.learnification.idgenerator.PersistentIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;

public class PendingIntentIdGenerator extends PersistentIdGenerator {
    private static final String ID_TYPE = "pending-intent";

    public PendingIntentIdGenerator(AndroidLogger logger, LearnificationAppDatabase database) {
        super(logger, database, ID_TYPE);
    }
}
