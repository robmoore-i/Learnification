package com.rrm.learnification.notification;

import android.app.NotificationManager;

import java.util.Arrays;

public class AndroidActiveNotificationReader implements ActiveNotificationReader {
    private final NotificationManager systemNotificationManager;

    public AndroidActiveNotificationReader(NotificationManager systemNotificationManager) {
        this.systemNotificationManager = systemNotificationManager;
    }

    @Override
    public boolean hasActiveLearnifications() {
        return Arrays.stream(systemNotificationManager.getActiveNotifications())
                .map(statusBarNotification -> new AndroidNotification(
                        statusBarNotification.getPackageName(),
                        statusBarNotification.getNotification().extras.getString(LearnificationResponseNotificationFactory.NOTIFICATION_TYPE)))
                .anyMatch(AndroidNotification::isLearnification);
    }
}
