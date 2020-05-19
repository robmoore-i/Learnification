package com.rrm.learnification.learnification.publication;

import android.app.Notification;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.AndroidNotificationPublisher;

class LearnificationPublisher {
    private static final String LOG_TAG = "LearnificationPublisher";

    private final AndroidLogger logger;
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final AndroidNotificationFactory notificationFactory;
    private final AndroidNotificationPublisher notificationPublisher;

    LearnificationPublisher(AndroidLogger logger, LearnificationTextGenerator learnificationTextGenerator, AndroidNotificationFactory notificationFactory, AndroidNotificationPublisher notificationPublisher) {
        this.logger = logger;
        this.learnificationTextGenerator = learnificationTextGenerator;
        this.notificationFactory = notificationFactory;
        this.notificationPublisher = notificationPublisher;
    }

    void publishLearnification() {
        try {
            LearnificationText learnificationText = learnificationTextGenerator.learnificationText();

            Notification notification = notificationFactory.createLearnification(learnificationText);

            notificationPublisher.publish(notification);
        } catch (IllegalStateException e) {
            logger.i(LOG_TAG, "didn't publish a learnification because '" + e.getMessage() + "'");
        } catch (Exception e) {
            logger.e(LOG_TAG, e);
        }
    }
}
