package com.rrm.learnification.notification;

import com.rrm.learnification.idgenerator.IdGenerator;
import com.rrm.learnification.idgenerator.JavaInMemoryIdGenerator;

class NotificationIdGenerator {
    private static final NotificationIdGenerator instance = new NotificationIdGenerator();

    private final IdGenerator idGenerator = new JavaInMemoryIdGenerator();

    private NotificationIdGenerator() {
    }

    static NotificationIdGenerator getInstance() {
        return instance;
    }

    int nextNotificationId() {
        return idGenerator.nextId();
    }

    int lastNotificationId() {
        return idGenerator.lastId();
    }
}
