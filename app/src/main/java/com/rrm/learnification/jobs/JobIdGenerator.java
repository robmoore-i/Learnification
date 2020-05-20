package com.rrm.learnification.jobs;

import com.rrm.learnification.idgenerator.PersistentIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

public class JobIdGenerator extends PersistentIdGenerator {
    private static final String ID_TYPE = "jobs";

    public JobIdGenerator(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        super(logger, fileStorageAdaptor, ID_TYPE);
    }
}