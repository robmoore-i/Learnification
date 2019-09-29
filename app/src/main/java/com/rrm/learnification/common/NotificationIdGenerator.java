package com.rrm.learnification.common;

class NotificationIdGenerator {
    private static NotificationIdGenerator instance = new NotificationIdGenerator();

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
