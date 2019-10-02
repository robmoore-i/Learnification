package com.rrm.learnification.learnification;

import android.app.Notification;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearnificationText;

public class LearnificationPublisher {
    private static final String LOG_TAG = "LearnificationPublisher";

    private final AndroidLogger logger;
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final AndroidNotificationFacade androidNotificationFacade;

    public LearnificationPublisher(AndroidLogger logger, LearnificationTextGenerator learnificationTextGenerator, AndroidNotificationFacade androidNotificationFacade) {
        this.logger = logger;
        this.learnificationTextGenerator = learnificationTextGenerator;
        this.androidNotificationFacade = androidNotificationFacade;
    }

    public void publishLearnification() {
        try {
            LearnificationText learnificationText = learnificationTextGenerator.learnificationText();

            Notification notification = androidNotificationFacade.createLearnification(learnificationText);

            androidNotificationFacade.publish(notification);
        } catch (CantGenerateNotificationTextException e) {
            logger.e(LOG_TAG, e);
        }
    }
}
