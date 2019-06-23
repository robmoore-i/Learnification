package com.rrm.learnification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

class AndroidNotificationManager {
    private static final String LOG_TAG = "AndroidNotificationManager";

    private final String channelId;
    private final AndroidLogger androidLogger;
    private final AndroidLearnificationFactory androidLearnificationFactory;
    private final NotificationManagerCompat notificationManager;
    private final NotificationIdGenerator notificationIdGenerator;
    private final AndroidPackageContext androidPackageContext;

    AndroidNotificationManager(Context packageContext, String channelId, NotificationIdGenerator notificationIdGenerator, AndroidLogger androidLogger) {
        this.channelId = channelId;
        this.androidLogger = androidLogger;
        this.androidLearnificationFactory = new AndroidLearnificationFactory(packageContext, channelId, androidLogger);
        this.notificationManager = NotificationManagerCompat.from(packageContext);
        this.notificationIdGenerator = notificationIdGenerator;
        this.androidPackageContext = new AndroidPackageContext(packageContext.getApplicationContext());
    }

    void createNotificationChannel() {
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

    void createNotification() {
        Notification notification = androidLearnificationFactory.create(
                "This is a learnification",
                "It's a notification that is designed to help you learn"
        );

        notificationManager.notify(notificationIdGenerator.nextNotificationId(), notification);
    }
}
