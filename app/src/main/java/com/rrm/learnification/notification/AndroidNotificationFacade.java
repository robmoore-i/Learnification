package com.rrm.learnification.notification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.logger.AndroidLogger;

public class AndroidNotificationFacade {

    private final AndroidNotificationPublisher publisher;
    private final AndroidNotificationFactory factory;

    private AndroidNotificationFacade(AndroidNotificationFactory factory, AndroidNotificationPublisher publisher) {
        this.factory = factory;
        this.publisher = publisher;
    }

    public static AndroidNotificationFacade fromContext(AndroidLogger logger, Context context, NotificationIdGenerator notificationIdGenerator, PendingIntentIdGenerator pendingIntentRequestCodeGenerator) {
        return new AndroidNotificationFacade(
                new AndroidNotificationFactory(logger, context, pendingIntentRequestCodeGenerator),
                new AndroidNotificationPublisher(logger, NotificationManagerCompat.from(context), notificationIdGenerator)
        );
    }

    public Notification createLearnification(LearnificationText learnificationText) {
        return factory.createLearnification(learnificationText);
    }

    public void publish(Notification notification) {
        publisher.publish(notification);
    }
}
