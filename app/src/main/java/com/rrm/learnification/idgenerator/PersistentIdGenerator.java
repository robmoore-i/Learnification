package com.rrm.learnification.idgenerator;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

public abstract class PersistentIdGenerator {
    private final IdGenerator idGenerator;

    public PersistentIdGenerator(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor, String idType) {
        idGenerator = new InternalStorageIdGenerator(logger, fileStorageAdaptor, idType);
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
