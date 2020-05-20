package com.rrm.learnification.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.learnification.response.NotificationTextContent;
import com.rrm.learnification.logger.AndroidLogger;

public class AndroidLearnificationUpdater implements LearnificationUpdater {
    private static final String LOG_TAG = "AndroidLearnificationUpdater";

    private final AndroidLogger logger;
    private final NotificationManagerCompat notificationManagerCompat;
    private final LearnificationResponseNotificationFactory learnificationResponseNotificationFactory;
    private final NotificationIdGenerator notificationIdGenerator;

    public AndroidLearnificationUpdater(
            AndroidLogger logger,
            NotificationManagerCompat notificationManagerCompat,
            LearnificationResponseNotificationFactory learnificationResponseNotificationFactory,
            NotificationIdGenerator notificationIdGenerator) {
        this.logger = logger;
        this.notificationManagerCompat = notificationManagerCompat;
        this.learnificationResponseNotificationFactory = learnificationResponseNotificationFactory;
        this.notificationIdGenerator = notificationIdGenerator;
    }

    @Override
    public void updateLatestWithReply(NotificationTextContent replyContent, String givenPrompt, String expectedUserResponse) {
        Notification responseNotification = learnificationResponseNotificationFactory.createLearnificationResponse(replyContent, givenPrompt, expectedUserResponse);
        int lastId = notificationIdGenerator.last();
        logger.i(LOG_TAG, "updating notification with given prompt '" + givenPrompt + "' and notification id " + lastId);
        notificationManagerCompat.notify(lastId, responseNotification);

    }
}
