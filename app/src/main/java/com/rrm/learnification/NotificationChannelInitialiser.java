package com.rrm.learnification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

class NotificationChannelInitialiser {
    private static final String LOG_TAG = "NotificationChannelInitialiser";

    private final AndroidPackageContext androidPackageContext;
    private final AndroidLogger androidLogger;

    NotificationChannelInitialiser(AndroidPackageContext androidPackageContext, AndroidLogger androidLogger) {
        this.androidPackageContext = androidPackageContext;
        this.androidLogger = androidLogger;
    }

    void createNotificationChannel(String channelId) {
        androidLogger.v(LOG_TAG, "Creating notification channel");

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = androidPackageContext.getNotificationChannelName();
            String description = androidPackageContext.getNotificationChannelDescription();
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = androidPackageContext.getNotificationManager();
            notificationManager.createNotificationChannel(channel);
        }

        androidLogger.v(LOG_TAG, "Created notification channel");
    }
}
