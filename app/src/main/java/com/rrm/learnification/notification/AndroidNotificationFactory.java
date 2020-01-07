package com.rrm.learnification.notification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.rrm.learnification.R;
import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.response.NotificationTextContent;

import java.util.List;

public class AndroidNotificationFactory {
    public static final String NOTIFICATION_TYPE = "notificationType";
    private static final String LOG_TAG = "AndroidNotificationFactory";

    private final AndroidLogger logger;
    private final Context packageContext;
    private final AndroidNotificationActionFactory notificationActionFactory;


    public AndroidNotificationFactory(AndroidLogger logger, Context packageContext, PendingIntentRequestCodeGenerator pendingIntentRequestCodeGenerator) {
        this.logger = logger;
        this.packageContext = packageContext;
        this.notificationActionFactory = new AndroidNotificationActionFactory(packageContext, pendingIntentRequestCodeGenerator);
    }

    public Notification createLearnification(LearnificationText learnificationText) {
        String learningItemPrompt = learnificationText.given;
        String subHeading = learnificationText.subHeading;
        String expectedUserResponse = learnificationText.expected;
        logger.v(LOG_TAG, "Creating a notification with title '" + learningItemPrompt + "' and text '" + subHeading + "'");

        return buildNotification(
                learningItemPrompt,
                subHeading,
                notificationActionFactory.learnificationActions(learningItemPrompt, expectedUserResponse));
    }

    public Notification createLearnificationResponse(NotificationTextContent notificationTextContent) {
        return appNotificationTemplate(notificationTextContent.title(), notificationTextContent.text(), NotificationType.LEARNIFICATION_RESPONSE)
                .build();
    }

    private Notification buildNotification(String title, String text, List<NotificationCompat.Action> actions) {
        NotificationCompat.Builder builder = appNotificationTemplate(title, text, NotificationType.LEARNIFICATION);
        actions.forEach(builder::addAction);
        return builder.build();
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

    private PendingIntent notificationContentIntent() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }

    private Bundle notificationExtras(String notificationType) {
        Bundle bundle = new Bundle();
        bundle.putString(NOTIFICATION_TYPE, notificationType);
        return bundle;
    }
}
