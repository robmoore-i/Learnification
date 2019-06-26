package com.rrm.learnification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

class AndroidLearnificationFactory {
    private static final String LOG_TAG = "AndroidLearnificationFactory";

    // Key for the string that's delivered in the reply action's intent.
    static final String REPLY_TEXT = "key_text_reply";

    private final String channelId;
    private final AndroidLogger androidLogger;
    private final AndroidLearnificationCreationContext context;

    AndroidLearnificationFactory(Context packageContext, String channelId, AndroidLogger androidLogger) {
        this.channelId = channelId;
        this.androidLogger = androidLogger;
        this.context = new AndroidLearnificationCreationContext(packageContext);
    }

    Notification create(String title, String text) {
        androidLogger.v(LOG_TAG, "Creating a notification with title '" + title + "' and text '" + text + "'");

        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_TEXT)
                .setLabel(context.getRemoteInputReplyLabel())
                .build();

        // Create the reply action and add the remote input.
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                context.getReplyActionLabel(),
                context.responsePendingIntent())
                .addRemoteInput(remoteInput)
                .build();

        return context.buildNotification(channelId, title, text, replyAction);
    }
}
