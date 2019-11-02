package com.rrm.learnification.notification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.R;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.response.NotificationTextContent;

public class AndroidNotificationFactory {
    public static final String NOTIFICATION_TYPE = "notificationType";
    public static final String REPLY_TEXT = "remote_input_text_reply";
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
        String expectedUserResponse = learnificationText.expected;
        logger.v(LOG_TAG, "Creating a notification with title '" + learningItemPrompt + "' and text '" + subHeading + "'");

        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_TEXT)
                .setLabel(replyActionLabel())
                .build();

        // Create the reply action and add the remote input.
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                replyActionLabel(),
                learnificationIntent(expectedUserResponse, learningItemPrompt))
                .addRemoteInput(remoteInput)
                .build();

        // Create the show-me action
        NotificationCompat.Action skipAction = new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                "Show me",
                showMeIntent(expectedUserResponse, learningItemPrompt))
                .build();

        // Use the title for the learnification main text, so it shows up boldly.
        return buildNotification(learningItemPrompt, subHeading, replyAction, skipAction);
    }

    private String replyActionLabel() {
        return packageContext.getString(R.string.reply_label);
    }

    private PendingIntent showMeIntent(String expectedUserResponse, String learningItemPrompt) {
        return AndroidPendingIntentBuilder.showMeIntent(packageContext)
                .withExpectedUserResponse(expectedUserResponse)
                .withLearningItemPrompt(learningItemPrompt)
                .build();
    }

    private PendingIntent learnificationIntent(String expectedUserResponse, String learningItemPrompt) {
        return AndroidPendingIntentBuilder.learnificationIntent(packageContext)
                .withExpectedUserResponse(expectedUserResponse)
                .withLearningItemPrompt(learningItemPrompt)
                .build();
    }

    public Notification createLearnificationResponse(NotificationTextContent notificationTextContent) {
        return appNotificationTemplate(notificationTextContent.title(), notificationTextContent.text(), NotificationType.LEARNIFICATION_RESPONSE)
                .build();
    }

    private Notification buildNotification(String title, String text, NotificationCompat.Action replyAction, NotificationCompat.Action skipAction) {
        return appNotificationTemplate(title, text, NotificationType.LEARNIFICATION)
                .addAction(replyAction)
                .addAction(skipAction)
                .build();
    }

    private NotificationCompat.Builder appNotificationTemplate(String title, String text, String notificationType) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(packageContext, AndroidNotificationFacade.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationContentIntent()) // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true)
                .addExtras(notificationExtras(notificationType));

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_a_notification);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(packageContext.getResources(), R.mipmap.ic_launcher_a_notification));
        return notificationBuilder;
    }

    private Bundle notificationExtras(String notificationType) {
        Bundle bundle = new Bundle();
        bundle.putString(NOTIFICATION_TYPE, notificationType);
        return bundle;
    }

    private PendingIntent notificationContentIntent() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }
}
