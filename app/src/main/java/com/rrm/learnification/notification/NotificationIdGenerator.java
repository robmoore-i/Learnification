package com.rrm.learnification.notification;

import com.rrm.learnification.idgenerator.IdGenerator;
import com.rrm.learnification.idgenerator.InternalStorageIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

public class NotificationIdGenerator {
    private static final String ID_TYPE = "notifications";

    private final IdGenerator idGenerator;

    private NotificationIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public static NotificationIdGenerator fromFileStorageAdaptor(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        return new NotificationIdGenerator(new InternalStorageIdGenerator(logger, fileStorageAdaptor, NotificationIdGenerator.ID_TYPE));
    }

    int nextNotificationId() {
        return idGenerator.nextId();
    }

    int lastNotificationId() {
        return idGenerator.lastId();
    }

    public void reset() {
        idGenerator.reset();
    }
}
