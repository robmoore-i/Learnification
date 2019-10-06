package com.rrm.learnification.publication;

import android.app.Notification;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.notification.CantGenerateNotificationTextException;

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
        } catch (CantGenerateNotificationTextException e) {
            logger.e(LOG_TAG, e);
        }
    }
}
