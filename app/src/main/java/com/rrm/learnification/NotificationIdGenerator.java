package com.rrm.learnification;

class NotificationIdGenerator {
    private int nextId = 0;

    int getNotificationId() {
        return nextId++;
    }
}
