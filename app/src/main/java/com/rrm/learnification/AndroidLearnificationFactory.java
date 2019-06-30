package com.rrm.learnification;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

class AndroidLearnificationFactory {
    private static final String LOG_TAG = "AndroidLearnificationFactory";

    // Key for the string that's delivered in the reply action's intent.
    static final String REPLY_TEXT = "key_text_reply";

    private final AndroidLogger logger;
    private final AndroidLearnificationFactoryContext context;

    AndroidLearnificationFactory(AndroidLogger logger, AndroidLearnificationFactoryContext context) {
        this.logger = logger;
        this.context = context;
    }

    Notification create(String title, String text) {
        logger.v(LOG_TAG, "Creating a notification with title '" + title + "' and text '" + text + "'");

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

        return context.buildNotification(title, text, replyAction);
    }
}
