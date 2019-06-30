package com.rrm.learnification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

class AndroidNotificationFactory {
    private final Context packageContext;

    AndroidNotificationFactory(Context packageContext) {
        this.packageContext = packageContext;
    }

    String getRemoteInputReplyLabel() {
        return packageContext.getResources().getString(R.string.reply_label);
    }

    String replyActionLabel() {
        return packageContext.getString(R.string.reply_label);
    }

    PendingIntent responsePendingIntent() {
        return PendingIntent.getActivity(
                packageContext,
                0,
                new Intent(packageContext, LearnificationResponseActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    Notification buildNotification(String title, String text, NotificationCompat.Action replyAction) {
        return appNotificationTemplate(title, text)
                .addAction(replyAction)
                .build();
    }

    Notification buildResponseNotification(String title, String text) {
        return appNotificationTemplate(title, text)
                .build();
    }

    private NotificationCompat.Builder appNotificationTemplate(String title, String text) {
        return new NotificationCompat.Builder(packageContext, NotificationChannelInitialiser.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getNotificationLargeIcon())
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(notificationContentIntent())
                .setAutoCancel(true);
    }

    private Bitmap getNotificationLargeIcon() {
        return BitmapFactory.decodeResource(packageContext.getApplicationContext().getResources(), R.mipmap.ic_launcher);
    }

    private PendingIntent notificationContentIntent() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }
}
