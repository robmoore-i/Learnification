package com.rrm.learnification.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.learnification.response.NotificationTextContent;
import com.rrm.learnification.logger.AndroidLogger;

public class AndroidResponseNotificationCorrespondent implements ResponseNotificationCorrespondent {
    private static final String LOG_TAG = "AndroidResponseNotificationCorrespondent";

    private final AndroidLogger logger;
    private final NotificationManagerCompat notificationManagerCompat;
    private final AndroidNotificationFactory androidNotificationFactory;
    private final NotificationIdGenerator notificationIdGenerator;

    public AndroidResponseNotificationCorrespondent(
            AndroidLogger logger,
            NotificationManagerCompat notificationManagerCompat,
            AndroidNotificationFactory androidNotificationFactory,
            NotificationIdGenerator notificationIdGenerator) {
        this.logger = logger;
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
}
