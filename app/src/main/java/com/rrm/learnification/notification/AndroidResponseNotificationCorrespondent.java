package com.rrm.learnification.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.response.NotificationTextContent;

import java.util.Arrays;

public class AndroidResponseNotificationCorrespondent implements ResponseNotificationCorrespondent {
    private static final String LOG_TAG = "AndroidResponseNotificationCorrespondent";

    private final AndroidLogger logger;
    private final NotificationManager systemNotificationManager;
    private final NotificationManagerCompat notificationManagerCompat;
    private final AndroidNotificationFactory androidNotificationFactory;
    private final NotificationIdGenerator notificationIdGenerator;

    public AndroidResponseNotificationCorrespondent(
            AndroidLogger logger,
            NotificationManager systemNotificationManager,
            NotificationManagerCompat notificationManagerCompat,
            AndroidNotificationFactory androidNotificationFactory,
            NotificationIdGenerator notificationIdGenerator) {
        this.logger = logger;
        this.systemNotificationManager = systemNotificationManager;
        this.notificationManagerCompat = notificationManagerCompat;
        this.androidNotificationFactory = androidNotificationFactory;
        this.notificationIdGenerator = notificationIdGenerator;
    }

    @Override
    public void updateLatestWithReply(NotificationTextContent replyContent, String givenPrompt, String expectedUserResponse) {
        Notification responseNotification = androidNotificationFactory.createLearnificationResponse(replyContent, givenPrompt, expectedUserResponse);
        int lastId = notificationIdGenerator.last();
        logger.i(LOG_TAG, "updating notification with id " + lastId);
        notificationManagerCompat.notify(lastId, responseNotification);

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
