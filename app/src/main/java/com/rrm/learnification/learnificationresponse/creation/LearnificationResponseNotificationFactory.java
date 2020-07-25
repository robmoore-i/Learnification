package com.rrm.learnification.learnificationresponse.creation;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.rrm.learnification.R;
import com.rrm.learnification.learnification.creation.LearnificationNotificationChannel;
import com.rrm.learnification.learnificationresponse.response.LearnificationResultService;
import com.rrm.learnification.notification.IdentifiedNotification;
import com.rrm.learnification.notification.NotificationTextContent;
import com.rrm.learnification.notification.NotificationType;
import com.rrm.learnification.notification.PendingIntentIdGenerator;

import java.util.ArrayList;
import java.util.List;

public class LearnificationResponseNotificationFactory {
    public static final String GIVEN_PROMPT_EXTRA = "givenPrompt";
    public static final String EXPECTED_USER_RESPONSE_EXTRA = "expectedUserResponse";
    public static final String USER_REPORTS_THEY_ARE_CORRECT_EXTRA = "userReportsTheyAreCorrect";

    private final Context packageContext;
    private final PendingIntentIdGenerator pendingIntentRequestCodeGenerator;


    public LearnificationResponseNotificationFactory(Context packageContext, PendingIntentIdGenerator pendingIntentRequestCodeGenerator) {
        this.packageContext = packageContext;
        this.pendingIntentRequestCodeGenerator = pendingIntentRequestCodeGenerator;
    }

    public IdentifiedNotification createLearnificationResponse(NotificationTextContent notificationTextContent,
                                                               String givenPrompt, String expectedUserResponse, int notificationId) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(packageContext, LearnificationResponseNotificationChannel.CHANNEL_ID)
                .setContentTitle(notificationTextContent.title())
                .setContentText(notificationTextContent.text())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationContentIntent()) // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true);

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_a_notification);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(packageContext.getResources(), R.mipmap.ic_launcher_a_notification));
        learnificationResponseActions(givenPrompt, expectedUserResponse, notificationId).forEach(notificationBuilder::addAction);
        return new IdentifiedNotification(notificationId, notificationBuilder, notificationExtras());
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

    private List<NotificationCompat.Action> learnificationResponseActions(String givenPrompt, String expectedUserResponse, int notificationId) {
        List<NotificationCompat.Action> actions = new ArrayList<>();
        actions.add(pressButtonAction("My answer was ✅", learnificationResponsePendingIntent(givenPrompt, expectedUserResponse, true, notificationId)));
        actions.add(pressButtonAction("My answer was ❌", learnificationResponsePendingIntent(givenPrompt, expectedUserResponse, false, notificationId)));
        return actions;
    }

    private PendingIntent learnificationResponsePendingIntent(String learningItemPrompt, String expectedUserResponse,
                                                              boolean userReportsTheyAreCorrect, int notificationId) {
        Intent intent = new Intent(packageContext, LearnificationResultService.class);
        intent.putExtra(USER_REPORTS_THEY_ARE_CORRECT_EXTRA, userReportsTheyAreCorrect);
        intent.putExtra(EXPECTED_USER_RESPONSE_EXTRA, expectedUserResponse);
        intent.putExtra(GIVEN_PROMPT_EXTRA, learningItemPrompt);
        intent.putExtra(IdentifiedNotification.ID_EXTRA, notificationId);
        return PendingIntent.getService(
                packageContext,
                pendingIntentRequestCodeGenerator.next(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
