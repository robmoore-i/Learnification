package com.rrm.learnification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

class NotificationChannelInitialiser {
    private static final String LOG_TAG = "NotificationChannelInitialiser";

    private final AndroidNotificationContext context;
    private final AndroidLogger logger;

    NotificationChannelInitialiser(AndroidLogger logger, AndroidNotificationContext context) {
        this.context = context;
        this.logger = logger;
    }

    void createNotificationChannel(String channelId) {
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
}
