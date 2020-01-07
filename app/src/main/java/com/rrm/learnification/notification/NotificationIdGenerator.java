package com.rrm.learnification.notification;

import com.rrm.learnification.idgenerator.PersistentIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

public class NotificationIdGenerator extends PersistentIdGenerator {
    private static final String ID_TYPE = "notifications";

    private NotificationIdGenerator(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        super(logger, fileStorageAdaptor, ID_TYPE);
    }

    public static NotificationIdGenerator fromFileStorageAdaptor(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        return new NotificationIdGenerator(logger, fileStorageAdaptor);
    }
}
