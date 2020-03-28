package com.rrm.learnification.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.logger.AndroidLogger;

class AndroidNotificationPublisher {
    private static final String LOG_TAG = "AndroidNotificationPublisher";

    private final AndroidLogger logger;
    private final NotificationManagerCompat notificationManager;
    private final NotificationIdGenerator notificationIdGenerator;

    AndroidNotificationPublisher(AndroidLogger logger, NotificationManagerCompat notificationManager, NotificationIdGenerator notificationIdGenerator) {
        this.logger = logger;
        this.notificationManager = notificationManager;
        this.notificationIdGenerator = notificationIdGenerator;
    }

    void publish(Notification notification) {
        int nextId = notificationIdGenerator.next();
        logger.i(LOG_TAG, "publishing notification with id " + nextId);
        notificationManager.notify(nextId, notification);
    }
}
