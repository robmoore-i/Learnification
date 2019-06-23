package com.rrm.learnification;

class NotificationIdGenerator {
    private static NotificationIdGenerator instance = new NotificationIdGenerator();

    private int nextId = 0;
    private int lastId = 0;

    private NotificationIdGenerator() {
    }

    static NotificationIdGenerator getInstance() {
        return instance;
    }

    int nextNotificationId() {
        lastId = nextId;
        return nextId++;
    }

    void reset() {
        nextId = 0;
    }

    int lastNotificationId() {
        return lastId;
    }
}
