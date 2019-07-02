package com.rrm.learnification;

import android.app.Notification;

class AndroidLearnificationPublisher {
    private static final String LOG_TAG = "AndroidLearnificationPublisher";

    private final AndroidLogger logger;
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final AndroidNotificationManager notificationManager;
    private final NotificationIdGenerator notificationIdGenerator;
    private final LearnificationTextGenerator learnificationTextGenerator;

    AndroidLearnificationPublisher(AndroidLogger logger, AndroidLearnificationFactory androidLearnificationFactory, NotificationIdGenerator notificationIdGenerator, LearnificationTextGenerator learnificationTextGenerator, AndroidNotificationManager notificationManager) {
        this.logger = logger;
        this.androidLearnificationFactory = androidLearnificationFactory;
        this.notificationManager = notificationManager;
        this.notificationIdGenerator = notificationIdGenerator;
        this.learnificationTextGenerator = learnificationTextGenerator;
    }

    void createLearnification() {
        try {
            String notificationText = learnificationTextGenerator.notificationText();
            Notification notification = androidLearnificationFactory.create(
                    notificationText,
                    "Learn!"
            );

            notificationManager.notify(notificationIdGenerator.nextNotificationId(), notification);
        } catch (CantGenerateNotificationTextException e) {
            logger.e(LOG_TAG, e);
        }
    }
}
