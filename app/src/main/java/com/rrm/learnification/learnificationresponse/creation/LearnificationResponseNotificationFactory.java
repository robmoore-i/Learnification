package com.rrm.learnification.learnificationresponse.creation;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.rrm.learnification.R;
import com.rrm.learnification.learnification.response.NotificationTextContent;
import com.rrm.learnification.learnificationresponse.response.LearnificationResultService;
import com.rrm.learnification.notification.LearnificationNotificationChannelCreator;
import com.rrm.learnification.notification.NotificationType;
import com.rrm.learnification.notification.PendingIntentIdGenerator;

import java.util.ArrayList;
import java.util.List;

public class LearnificationResponseNotificationFactory {
    private static final String EXPECTED_USER_RESPONSE_EXTRA = "expectedUserResponse";
    private static final String GIVEN_PROMPT_EXTRA = "givenPrompt";

    private final Context packageContext;
    private final PendingIntentIdGenerator pendingIntentRequestCodeGenerator;


    public LearnificationResponseNotificationFactory(Context packageContext, PendingIntentIdGenerator pendingIntentRequestCodeGenerator) {
        this.packageContext = packageContext;
        this.pendingIntentRequestCodeGenerator = pendingIntentRequestCodeGenerator;
    }

    public Notification createLearnificationResponse(NotificationTextContent notificationTextContent, String givenPrompt, String expectedUserResponse) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(packageContext, LearnificationNotificationChannelCreator.CHANNEL_ID)
                .setContentTitle(notificationTextContent.title())
                .setContentText(notificationTextContent.text())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationContentIntent()) // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true)
                .addExtras(notificationExtras());

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_a_notification);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(packageContext.getResources(), R.mipmap.ic_launcher_a_notification));
        learnificationResponseActions(givenPrompt, expectedUserResponse).forEach(notificationBuilder::addAction);
        return notificationBuilder.build();
    }

    private PendingIntent notificationContentIntent() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }

    private Bundle notificationExtras() {
        Bundle bundle = new Bundle();
        bundle.putString(NotificationType.NOTIFICATION_TYPE_EXTRA_NAME, NotificationType.LEARNIFICATION_RESPONSE);
        return bundle;
    }

    private NotificationCompat.Action pressButtonAction(String title, PendingIntent pendingIntent) {
        return new NotificationCompat.Action.Builder(R.drawable.android_send, title, pendingIntent).build();
    }

    private List<NotificationCompat.Action> learnificationResponseActions(String givenPrompt, String expectedUserResponse) {
        List<NotificationCompat.Action> actions = new ArrayList<>();
        PendingIntent pendingIntent = learnificationResponsePendingIntent(givenPrompt, expectedUserResponse);
        actions.add(pressButtonAction("My answer was ✅", pendingIntent));
        actions.add(pressButtonAction("My answer was ❌", pendingIntent));
        return actions;
    }

    private PendingIntent learnificationResponsePendingIntent(String learningItemPrompt, String expectedUserResponse) {
        Intent intent = new Intent(packageContext, LearnificationResultService.class);
        intent.putExtra(EXPECTED_USER_RESPONSE_EXTRA, expectedUserResponse);
        intent.putExtra(GIVEN_PROMPT_EXTRA, learningItemPrompt);
        return PendingIntent.getService(
                packageContext,
                pendingIntentRequestCodeGenerator.next(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
