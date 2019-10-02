package com.rrm.learnification.notification;

import com.rrm.learnification.common.JavaInMemoryIdGenerator;

class NotificationIdGenerator {
    private static final NotificationIdGenerator instance = new NotificationIdGenerator();

    private final JavaInMemoryIdGenerator javaInMemoryIdGenerator = new JavaInMemoryIdGenerator();

    private NotificationIdGenerator() {
    }

    static NotificationIdGenerator getInstance() {
        return instance;
    }

    int nextNotificationId() {
        return javaInMemoryIdGenerator.nextId();
    }

    int lastNotificationId() {
        return javaInMemoryIdGenerator.lastId();
    }
}
