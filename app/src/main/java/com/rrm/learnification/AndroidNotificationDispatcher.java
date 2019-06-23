package com.rrm.learnification;

import android.app.PendingIntent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

class AndroidNotificationDispatcher {
    private String channelId;
    private MainActivity packageContext;

    AndroidNotificationDispatcher(String channelId, MainActivity packageContext) {
        this.channelId = channelId;
        this.packageContext = packageContext;
    }

    void sendNotification(String title, String text, int notificationId, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(packageContext, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(packageContext.getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(packageContext);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }
}
