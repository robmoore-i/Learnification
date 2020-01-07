package com.rrm.learnification.notification;

import com.rrm.learnification.idgenerator.IdGenerator;
import com.rrm.learnification.idgenerator.InternalStorageIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

public class PendingIntentRequestCodeGenerator {
    private static final String ID_TYPE = "pending-intent";

    private final IdGenerator idGenerator;

    private PendingIntentRequestCodeGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public static PendingIntentRequestCodeGenerator fromFileStorageAdaptor(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        return new PendingIntentRequestCodeGenerator(new InternalStorageIdGenerator(logger, fileStorageAdaptor, PendingIntentRequestCodeGenerator.ID_TYPE));
    }

    int nextRequestCode() {
        return idGenerator.nextId();
    }
}
