package com.rrm.learnification.learnificationresponse.publication;

import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.learnification.response.NotificationTextContent;
import com.rrm.learnification.learnificationresponse.creation.LearnificationResponseNotificationFactory;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.IdentifiedNotification;

public class AndroidLearnificationUpdater implements LearnificationUpdater {
    private static final String LOG_TAG = "AndroidLearnificationUpdater";

    private final AndroidLogger logger;
    private final NotificationManagerCompat notificationManagerCompat;
    private final LearnificationResponseNotificationFactory learnificationResponseNotificationFactory;

    public AndroidLearnificationUpdater(AndroidLogger logger, NotificationManagerCompat notificationManagerCompat,
                                        LearnificationResponseNotificationFactory learnificationResponseNotificationFactory) {
        this.logger = logger;
        this.notificationManagerCompat = notificationManagerCompat;
        this.learnificationResponseNotificationFactory = learnificationResponseNotificationFactory;
    }

    @Override
    public void updateWithResponse(NotificationTextContent replyContent, String givenPrompt, String expectedUserResponse, int notificationId) {
        IdentifiedNotification learnificationResponse = learnificationResponseNotificationFactory.createLearnificationResponse(replyContent,
                givenPrompt, expectedUserResponse, notificationId);
        logger.i(LOG_TAG, "updating notification with given prompt '" + givenPrompt + "' and notification id " + notificationId);
        notificationManagerCompat.notify(learnificationResponse.id(), learnificationResponse.notification());

    }
}
