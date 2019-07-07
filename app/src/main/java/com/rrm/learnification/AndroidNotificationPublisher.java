package com.rrm.learnification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

class AndroidNotificationPublisher {
    private final NotificationManagerCompat notificationManager;
    private NotificationIdGenerator notificationIdGenerator;

    AndroidNotificationPublisher(NotificationManagerCompat notificationManager, NotificationIdGenerator notificationIdGenerator) {
        this.notificationManager = notificationManager;
        this.notificationIdGenerator = notificationIdGenerator;
    }

    void publish(Notification notification) {
        notificationManager.notify(notificationIdGenerator.nextNotificationId(), notification);
    }
}
