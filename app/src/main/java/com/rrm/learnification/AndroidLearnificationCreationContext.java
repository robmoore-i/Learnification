package com.rrm.learnification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

class AndroidLearnificationCreationContext {
    private final Context packageContext;

    AndroidLearnificationCreationContext(Context packageContext) {
        this.packageContext = packageContext;
    }

    String getRemoteInputReplyLabel() {
        return packageContext.getResources().getString(R.string.reply_label);
    }

    String getReplyActionLabel() {
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

    Notification buildNotification(String channelId, String title, String text, NotificationCompat.Action replyAction) {
        return new NotificationCompat.Builder(packageContext, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getNotificationLargeIcon())
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(notificationContentIntent())
                .setAutoCancel(true)
                .addAction(replyAction)
                .build();
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
