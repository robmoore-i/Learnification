package com.rrm.learnification;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

class AndroidNotificationsManager {
    private final String channelId;
    private final MainActivity packageContext;

    AndroidNotificationsManager(String channelId, MainActivity packageContext) {
        this.channelId = channelId;
        this.packageContext = packageContext;
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
        AndroidNotificationDispatcher androidNotificationDispatcher = new AndroidNotificationDispatcher(channelId, this.packageContext);
        androidNotificationDispatcher.sendNotification("This is a Learnification", "It's a notification that is designed to help you learn", 0, notificationContentIntent());
    }

    private PendingIntent notificationContentIntent() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }

}
