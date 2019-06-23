package com.rrm.learnification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

class AndroidLearnificationManager {
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final NotificationManagerCompat notificationManager;
    private final NotificationIdGenerator notificationIdGenerator;

    AndroidLearnificationManager(AndroidLearnificationFactory androidLearnificationFactory, NotificationIdGenerator notificationIdGenerator, NotificationManagerCompat notificationManager) {
        this.androidLearnificationFactory = androidLearnificationFactory;
        this.notificationManager = notificationManager;
        this.notificationIdGenerator = notificationIdGenerator;
    }

    void createNotification() {
        Notification notification = androidLearnificationFactory.create(
                "This is a learnification",
                "It's a notification that is designed to help you learn"
        );

        notificationManager.notify(notificationIdGenerator.nextNotificationId(), notification);
    }
}
