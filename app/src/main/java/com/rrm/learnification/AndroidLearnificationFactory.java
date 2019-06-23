package com.rrm.learnification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

class AndroidLearnificationFactory {
    private static final String LOG_TAG = "AndroidLearnificationFactory";

    // Key for the string that's delivered in the reply action's intent.
    private static final String REPLY_TEXT = "key_text_reply";


    private final String channelId;
    private final MainActivity packageContext;
    private final AndroidLogger androidLogger;

    AndroidLearnificationFactory(MainActivity packageContext, String channelId, AndroidLogger androidLogger) {
        this.channelId = channelId;
        this.packageContext = packageContext;
        this.androidLogger = androidLogger;
    }

    Notification create(String title, String text) {
        androidLogger.v(LOG_TAG, "Creating a notification with title '" + title + "' and text '" + text + "'");

        String replyLabel = packageContext.getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_TEXT)
                .setLabel(replyLabel)
                .build();

        PendingIntent replyPendingIntent = PendingIntent.getActivity(
                packageContext,
                0,
                new Intent(packageContext, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Create the reply action and add the remote input.
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                packageContext.getString(R.string.reply_label),
                replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        return new NotificationCompat.Builder(packageContext, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(packageContext.getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(notificationContentIntent())
                .setAutoCancel(true)
                .addAction(replyAction)
                .build();
    }

    private PendingIntent notificationContentIntent() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }
}
