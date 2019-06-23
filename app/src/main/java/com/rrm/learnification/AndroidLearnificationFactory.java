package com.rrm.learnification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

class AndroidLearnificationFactory {
    private static final String LOG_TAG = "AndroidLearnificationFactory";

    // Key for the string that's delivered in the reply action's intent.
    static final String REPLY_TEXT = "key_text_reply";


    private final String channelId;
    private final Context packageContext;
    private final AndroidLogger androidLogger;
    private final AndroidPackageContext androidPackageContext;

    AndroidLearnificationFactory(Context packageContext, String channelId, AndroidLogger androidLogger) {
        this.channelId = channelId;
        this.packageContext = packageContext;
        this.androidLogger = androidLogger;
        this.androidPackageContext = new AndroidPackageContext(packageContext);
    }

    Notification create(String title, String text) {
        androidLogger.v(LOG_TAG, "Creating a notification with title '" + title + "' and text '" + text + "'");

        String replyLabel = androidPackageContext.getRemoteInputReplyLabel();
        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_TEXT)
                .setLabel(replyLabel)
                .build();

        PendingIntent replyPendingIntent = PendingIntent.getActivity(
                packageContext,
                0,
                new Intent(packageContext, LearnificationResponseActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Create the reply action and add the remote input.
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                androidPackageContext.getReplyActionLabel(),
                replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        return new NotificationCompat.Builder(packageContext, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(androidPackageContext.getNotificationLargeIcon())
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(androidPackageContext.notificationContentIntent())
                .setAutoCancel(true)
                .addAction(replyAction)
                .build();
    }

}
