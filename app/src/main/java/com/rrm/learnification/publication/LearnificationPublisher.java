package com.rrm.learnification.publication;

import android.app.Notification;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFacade;

class LearnificationPublisher {
    private static final String LOG_TAG = "LearnificationPublisher";

    private final AndroidLogger logger;
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final AndroidNotificationFacade androidNotificationFacade;

    LearnificationPublisher(AndroidLogger logger, LearnificationTextGenerator learnificationTextGenerator, AndroidNotificationFacade androidNotificationFacade) {
        this.logger = logger;
        this.learnificationTextGenerator = learnificationTextGenerator;
        this.androidNotificationFacade = androidNotificationFacade;
    }

    void publishLearnification() {
        try {
            LearnificationText learnificationText = learnificationTextGenerator.learnificationText();

            Notification notification = androidNotificationFacade.createLearnification(learnificationText);

            androidNotificationFacade.publish(notification);
        } catch (IllegalStateException e) {
            logger.i(LOG_TAG, "didn't publish a learnification because '" + e.getMessage() + "'");
        } catch (Exception e) {
            logger.e(LOG_TAG, e);
        }
    }
}
