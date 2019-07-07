package com.rrm.learnification;

import android.app.Notification;

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
            String notificationText = learnificationTextGenerator.notificationText();

            // Use the title for the learnification main text, so it shows up boldly.
            Notification notification = androidNotificationFacade.createLearnification(
                    notificationText,
                    "Learn!"
            );

            androidNotificationFacade.publish(notification);
        } catch (CantGenerateNotificationTextException e) {
            logger.e(LOG_TAG, e);
        }
    }
}
