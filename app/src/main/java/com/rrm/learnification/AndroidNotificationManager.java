package com.rrm.learnification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

class AndroidNotificationManager {
    private final String channelId;
    private final MainActivity packageContext;
    private final AndroidLearnificationFactory androidLearnificationFactory;

    AndroidNotificationManager(String channelId, MainActivity packageContext) {
        this.channelId = channelId;
        this.packageContext = packageContext;
        this.androidLearnificationFactory = new AndroidLearnificationFactory(channelId, packageContext);
    }

    void createNotificationChannel() {
        Context context = packageContext.getApplicationContext();
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    void createNotification() {
        Notification notification = androidLearnificationFactory.create(
                "This is a Learnification",
                "It's a notification that is designed to help you learn"
        );

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(packageContext);
        notificationManager.notify(0, notification);
    }
}
