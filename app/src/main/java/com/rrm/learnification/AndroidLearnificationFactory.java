package com.rrm.learnification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

class AndroidLearnificationFactory {
    private static final String LOG_TAG = "AndroidLearnificationFactory";

    private final String channelId;
    private final MainActivity packageContext;
    private final AndroidLogger androidLogger;

    AndroidLearnificationFactory(String channelId, MainActivity packageContext, AndroidLogger androidLogger) {
        this.channelId = channelId;
        this.packageContext = packageContext;
        this.androidLogger = androidLogger;
    }

    Notification create(String title, String text) {
        androidLogger.v(LOG_TAG, "Creating a notification with title '" + title + "' and text '" + text + "'");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(packageContext, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(packageContext.getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(notificationContentIntent())
                .setAutoCancel(true);

        return builder.build();
    }

    private PendingIntent notificationContentIntent() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }
}
