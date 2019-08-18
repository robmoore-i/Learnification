package com.rrm.learnification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

class AndroidNotificationFactory {
    static final String EXPECTED_USER_RESPONSE_EXTRA = "expectedUserResponse";

    // Key for the string that's delivered in the reply action's intent.
    static final String REPLY_TEXT = "key_text_reply";
    private static final String LOG_TAG = "AndroidNotificationFactory";

    private final AndroidLogger logger;
    private final Context packageContext;

    AndroidNotificationFactory(AndroidLogger logger, Context packageContext) {
        this.logger = logger;
        this.packageContext = packageContext;
    }

    Notification createLearnification(LearnificationText learnificationText) {
        String learningItemPrompt = learnificationText.expected;
        String subHeading = learnificationText.subHeading;
        logger.v(LOG_TAG, "Creating a notification with title '" + learningItemPrompt + "' and text '" + subHeading + "'");

        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_TEXT)
                .setLabel(getRemoteInputReplyLabel())
                .build();

        // Create the reply action and add the remote input.
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                replyActionLabel(),
                responsePendingIntent(learnificationText.actual))
                .addRemoteInput(remoteInput)
                .build();

        // Use the title for the learnification main text, so it shows up boldly.
        return buildNotification(learningItemPrompt, subHeading, replyAction);
    }

    private String getRemoteInputReplyLabel() {
        return packageContext.getResources().getString(R.string.reply_label);
    }

    private String replyActionLabel() {
        return packageContext.getString(R.string.reply_label);
    }

    private PendingIntent responsePendingIntent(String expectedUserResponse) {
        Intent intent = new Intent(packageContext, LearnificationResponseService.class);
        intent.putExtra(EXPECTED_USER_RESPONSE_EXTRA, expectedUserResponse);
        return PendingIntent.getService(
                packageContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private Notification buildNotification(String title, String text, NotificationCompat.Action replyAction) {
        return appNotificationTemplate(title, text)
                .addAction(replyAction)
                .build();
    }

    Notification buildResponseNotification(ResponseNotificationContent responseNotificationContent) {
        return appNotificationTemplate(responseNotificationContent.title(), responseNotificationContent.text())
                .build();
    }

    private NotificationCompat.Builder appNotificationTemplate(String title, String text) {
        return new NotificationCompat.Builder(packageContext, AndroidNotificationFacade.CHANNEL_ID)
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
