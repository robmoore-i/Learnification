package com.rrm.learnification.notification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.R;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.response.LearnificationResponseService;
import com.rrm.learnification.response.ResponseNotificationContent;

public class AndroidNotificationFactory {
    public static final String NOTIFICATION_TYPE = "notificationType";

    public static final String REPLY_TEXT = "remote_input_text_reply";
    public static final String EXPECTED_USER_RESPONSE_EXTRA = "expectedUserResponse";
    public static final String SKIPPED_FLAG_EXTRA = "skippedFlag";
    private static final String LOG_TAG = "AndroidNotificationFactory";

    private final AndroidLogger logger;
    private final Context packageContext;

    public AndroidNotificationFactory(AndroidLogger logger, Context packageContext) {
        this.logger = logger;
        this.packageContext = packageContext;
    }

    public Notification createLearnification(LearnificationText learnificationText) {
        String learningItemPrompt = learnificationText.given;
        String subHeading = learnificationText.subHeading;
        logger.v(LOG_TAG, "Creating a notification with title '" + learningItemPrompt + "' and text '" + subHeading + "'");

        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_TEXT)
                .setLabel(getRemoteInputReplyLabel())
                .build();

        // Create the reply action and add the remote input.
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                replyActionLabel(),
                responsePendingIntent(learnificationText.expected))
                .addRemoteInput(remoteInput)
                .build();

        // Create the skip action
        NotificationCompat.Action skipAction = new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                "Skip",
                skipIntent())
                .build();

        // Use the title for the learnification main text, so it shows up boldly.
        return buildNotification(learningItemPrompt, subHeading, replyAction, skipAction);
    }

    private PendingIntent skipIntent() {
        Intent intent = new Intent(packageContext, LearnificationResponseService.class);
        intent.putExtra(SKIPPED_FLAG_EXTRA, true);
        return PendingIntent.getService(
                packageContext,
                PendingIntentRequestCodeGenerator.getInstance().nextRequestCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
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
        intent.putExtra(SKIPPED_FLAG_EXTRA, false);
        return PendingIntent.getService(
                packageContext,
                PendingIntentRequestCodeGenerator.getInstance().nextRequestCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private Notification buildNotification(String title, String text, NotificationCompat.Action replyAction, NotificationCompat.Action skipAction) {
        return appNotificationTemplate(title, text, NotificationType.LEARNIFICATION)
                .addAction(replyAction)
                .addAction(skipAction)
                .build();
    }

    public Notification createLearnificationResponse(ResponseNotificationContent responseNotificationContent) {
        return appNotificationTemplate(responseNotificationContent.title(), responseNotificationContent.text(), NotificationType.LEARNIFICATION_RESPONSE)
                .build();
    }

    private NotificationCompat.Builder appNotificationTemplate(String title, String text, String notificationType) {
        return new NotificationCompat.Builder(packageContext, AndroidNotificationFacade.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getNotificationLargeIcon())
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationContentIntent()) // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true)
                .addExtras(notificationExtras(notificationType));
    }

    private Bundle notificationExtras(String notificationType) {
        Bundle bundle = new Bundle();
        bundle.putString(NOTIFICATION_TYPE, notificationType);
        return bundle;
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
