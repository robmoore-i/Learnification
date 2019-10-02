package com.rrm.learnification.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

public class AndroidNotificationManager implements NotificationManager {
    private final NotificationManagerCompat notificationManagerCompat;
    private final AndroidNotificationFactory androidNotificationFactory;

    public AndroidNotificationManager(NotificationManagerCompat notificationManagerCompat, AndroidNotificationFactory androidNotificationFactory) {
        this.notificationManagerCompat = notificationManagerCompat;
        this.androidNotificationFactory = androidNotificationFactory;
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
}
