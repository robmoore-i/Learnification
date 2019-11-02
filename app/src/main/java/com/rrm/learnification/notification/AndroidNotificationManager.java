package com.rrm.learnification.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.response.NotificationTextContent;

import java.util.Arrays;

public class AndroidNotificationManager implements NotificationManager {
    private final android.app.NotificationManager systemNotificationManager;
    private final NotificationManagerCompat notificationManagerCompat;
    private final AndroidNotificationFactory androidNotificationFactory;

    public AndroidNotificationManager(android.app.NotificationManager systemNotificationManager, NotificationManagerCompat notificationManagerCompat, AndroidNotificationFactory androidNotificationFactory) {
        this.systemNotificationManager = systemNotificationManager;
        this.notificationManagerCompat = notificationManagerCompat;
        this.androidNotificationFactory = androidNotificationFactory;
    }

    public AndroidNotificationManager(android.app.NotificationManager systemNotificationManager, NotificationManagerCompat notificationManagerCompat, AndroidNotificationFacade androidNotificationFacade) {
        this.systemNotificationManager = systemNotificationManager;
        this.notificationManagerCompat = notificationManagerCompat;
        this.androidNotificationFactory = androidNotificationFacade.factory;
    }

    @Override
    public void updateLatestWithReply(NotificationTextContent replyContent) {
        Notification responseNotification = androidNotificationFactory.createLearnificationResponse(replyContent);
        notificationManagerCompat.notify(NotificationIdGenerator.getInstance().lastNotificationId(), responseNotification);
    }

    @Override
    public boolean hasActiveLearnifications() {
        return Arrays.stream(systemNotificationManager.getActiveNotifications())
                .map(statusBarNotification -> new AndroidNotification(
                        statusBarNotification.getPackageName(),
                        statusBarNotification.getNotification().extras.getString(AndroidNotificationFactory.NOTIFICATION_TYPE)))
                .anyMatch(AndroidNotification::isLearnification);
    }
}
