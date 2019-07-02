package com.rrm.learnification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

class AndroidNotificationManager {
    private final NotificationManagerCompat notificationManager;

    AndroidNotificationManager(NotificationManagerCompat notificationManager) {
        this.notificationManager = notificationManager;
    }

    void notify(int nextNotificationId, Notification notification) {
        notificationManager.notify(nextNotificationId, notification);
    }
}
