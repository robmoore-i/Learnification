package com.rrm.learnification.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearnificationText;

public class AndroidNotificationFacade {
    public static final String CHANNEL_ID = "learnification";

    private static final String LOG_TAG = "AndroidNotificationFacade";

    private final AndroidLogger logger;
    private final AndroidNotificationContext context;
    private final AndroidNotificationFactory factory;
    private final AndroidNotificationPublisher publisher;

    private AndroidNotificationFacade(AndroidLogger logger, AndroidNotificationContext context, AndroidNotificationFactory factory, AndroidNotificationPublisher publisher) {
        this.logger = logger;
        this.context = context;
        this.factory = factory;
        this.publisher = publisher;
    }

    public static AndroidNotificationFacade fromContext(AndroidLogger logger, Context context) {
        return new AndroidNotificationFacade(
                logger,
                new AndroidNotificationContext(context.getApplicationContext()),
                new AndroidNotificationFactory(logger, context),
                new AndroidNotificationPublisher(NotificationManagerCompat.from(context), NotificationIdGenerator.getInstance())
        );
    }

    public void createNotificationChannel(String channelId) {
        logger.v(LOG_TAG, "Creating notification channel");

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getNotificationChannelName();
            String description = context.getNotificationChannelDescription();
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getNotificationManager();
            notificationManager.createNotificationChannel(channel);
        }

        logger.v(LOG_TAG, "Created notification channel");
    }

    public Notification createLearnification(LearnificationText learnificationText) {
        return factory.createLearnification(learnificationText);
    }

    public void publish(Notification notification) {
        publisher.publish(notification);
    }
}
