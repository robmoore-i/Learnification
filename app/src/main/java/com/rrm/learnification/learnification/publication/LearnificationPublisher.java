package com.rrm.learnification.learnification.publication;

import com.rrm.learnification.learnification.creation.LearnificationFactory;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationPublisher;

class LearnificationPublisher {
    private static final String LOG_TAG = "LearnificationPublisher";

    private final AndroidLogger logger;
    private final AndroidNotificationPublisher notificationPublisher;
    private final LearnificationFactory learnificationFactory;

    LearnificationPublisher(AndroidLogger logger, AndroidNotificationPublisher notificationPublisher, LearnificationFactory learnificationFactory) {
        this.logger = logger;
        this.notificationPublisher = notificationPublisher;
        this.learnificationFactory = learnificationFactory;
    }

    void publishLearnification() {
        try {
            notificationPublisher.publish(learnificationFactory.getLearnification());
        } catch (IllegalStateException e) {
            logger.i(LOG_TAG, "didn't publish a learnification because '" + e.getMessage() + "'");
        } catch (Exception e) {
            logger.e(LOG_TAG, e);
        }
    }

}
