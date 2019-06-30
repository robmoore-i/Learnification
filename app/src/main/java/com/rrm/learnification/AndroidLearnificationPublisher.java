package com.rrm.learnification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

class AndroidLearnificationPublisher {
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final NotificationManagerCompat notificationManager;
    private final NotificationIdGenerator notificationIdGenerator;
    private final LearnificationTextGenerator learnificationTextGenerator;

    AndroidLearnificationPublisher(AndroidLearnificationFactory androidLearnificationFactory, NotificationIdGenerator notificationIdGenerator, LearnificationTextGenerator learnificationTextGenerator, NotificationManagerCompat notificationManager) {
        this.androidLearnificationFactory = androidLearnificationFactory;
        this.notificationManager = notificationManager;
        this.notificationIdGenerator = notificationIdGenerator;
        this.learnificationTextGenerator = learnificationTextGenerator;
    }

    void createLearnification() {
        Notification notification = androidLearnificationFactory.create(
                learnificationTextGenerator.notificationText(),
                "Learn!"
        );

        notificationManager.notify(notificationIdGenerator.nextNotificationId(), notification);
    }
}
