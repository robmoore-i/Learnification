package com.rrm.learnification.notification;

import com.rrm.learnification.files.FileStorageAdaptor;
import com.rrm.learnification.idgenerator.PersistentIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;

public class NotificationIdGenerator extends PersistentIdGenerator {
    private static final String ID_TYPE = "notifications";

    public NotificationIdGenerator(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        super(logger, fileStorageAdaptor, ID_TYPE);
    }
}
