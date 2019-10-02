package com.rrm.learnification.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

class AndroidNotificationPublisher {
    private final NotificationManagerCompat notificationManager;
    private final NotificationIdGenerator notificationIdGenerator;

    AndroidNotificationPublisher(NotificationManagerCompat notificationManager, NotificationIdGenerator notificationIdGenerator) {
        this.notificationManager = notificationManager;
        this.notificationIdGenerator = notificationIdGenerator;
    }

    void publish(Notification notification) {
        notificationManager.notify(notificationIdGenerator.nextNotificationId(), notification);
    }
}
