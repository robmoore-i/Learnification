package com.rrm.learnification.notification;

import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.logger.AndroidLogger;

public class AndroidNotificationPublisher {
    private static final String LOG_TAG = "AndroidNotificationPublisher";

    private final AndroidLogger logger;
    private final NotificationManagerCompat notificationManager;

    public AndroidNotificationPublisher(AndroidLogger logger, NotificationManagerCompat notificationManager) {
        this.logger = logger;
        this.notificationManager = notificationManager;
    }

    public void publish(IdentifiedNotification notification) {
        int nextId = notification.id();
        logger.i(LOG_TAG, "publishing notification with id " + nextId);
        notificationManager.notify(nextId, notification.notification());
    }
}
