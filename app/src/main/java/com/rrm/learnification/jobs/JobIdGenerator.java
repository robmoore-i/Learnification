package com.rrm.learnification.jobs;

import com.rrm.learnification.idgenerator.IdGenerator;
import com.rrm.learnification.idgenerator.InternalStorageIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

public class JobIdGenerator {
    private static final String ID_TYPE = "jobs";

    private final IdGenerator idGenerator;

    private JobIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public static JobIdGenerator fromFileStorageAdaptor(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        return new JobIdGenerator(new InternalStorageIdGenerator(logger, fileStorageAdaptor, JobIdGenerator.ID_TYPE));
    }

    int nextJobId() {
        return idGenerator.nextId();
    }

    public int lastJobId() {
        return idGenerator.lastId();
    }

    public void reset() {
        idGenerator.reset();
    }
}