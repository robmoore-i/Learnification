package com.rrm.learnification.notification;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationManagerCompat;

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
    public void cancelLatest() {
        notificationManagerCompat.cancel(NotificationIdGenerator.getInstance().lastNotificationId());
    }

    @Override
    public void updateLatestWithReply(ResponseNotificationContent replyContent) {
        Notification responseNotification = androidNotificationFactory.buildResponseNotification(replyContent);
        notificationManagerCompat.notify(NotificationIdGenerator.getInstance().lastNotificationId(), responseNotification);
    }

    @Override
    public boolean hasActiveLearnifications() {
        StatusBarNotification[] activeNotifications = systemNotificationManager.getActiveNotifications();
        for (StatusBarNotification activeNotification : activeNotifications) {
            if (activeNotification.getPackageName().equals("com.rrm.learnification")) {
                return true;
            }
        }
        return false;
    }
}
