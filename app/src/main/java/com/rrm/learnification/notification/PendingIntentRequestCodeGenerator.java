package com.rrm.learnification.notification;

import com.rrm.learnification.idgenerator.PersistentIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

public class PendingIntentRequestCodeGenerator extends PersistentIdGenerator {
    private static final String ID_TYPE = "pending-intent";

    private PendingIntentRequestCodeGenerator(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        super(logger, fileStorageAdaptor, ID_TYPE);
    }

    public static PendingIntentRequestCodeGenerator fromFileStorageAdaptor(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        return new PendingIntentRequestCodeGenerator(logger, fileStorageAdaptor);
    }
}
