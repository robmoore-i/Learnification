package com.rrm.learnification.learnification.creation;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.R;
import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.learnification.publication.LearnificationTextGenerator;
import com.rrm.learnification.learnification.response.LearnificationResponseService;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.IdentifiedNotification;
import com.rrm.learnification.notification.LearnificationNotificationChannelCreator;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.NotificationType;
import com.rrm.learnification.notification.PendingIntentIdGenerator;

import java.util.ArrayList;
import java.util.List;

import static com.rrm.learnification.learnification.creation.LearnificationResponseType.ANSWER;
import static com.rrm.learnification.learnification.creation.LearnificationResponseType.NEXT;
import static com.rrm.learnification.learnification.creation.LearnificationResponseType.SHOW_ME;

public class LearnificationFactory {

    private static final String LOG_TAG = "LearnificationNotificationFactory";

    public static final String REPLY_TEXT = "remote_input_text_reply";
    public static final String RESPONSE_TYPE_EXTRA = "responseType";
    public static final String GIVEN_PROMPT_EXTRA = "givenPrompt";
    public static final String EXPECTED_USER_RESPONSE_EXTRA = "expectedUserResponse";

    private final AndroidLogger logger;
    private final Context packageContext;
    private final PendingIntentIdGenerator pendingIntentRequestCodeGenerator;
    private final NotificationIdGenerator notificationIdGenerator;
    private final LearnificationTextGenerator learnificationTextGenerator;

    public LearnificationFactory(AndroidLogger logger, Context packageContext,
                                 PendingIntentIdGenerator pendingIntentRequestCodeGenerator,
                                 NotificationIdGenerator notificationIdGenerator,
                                 LearnificationTextGenerator learnificationTextGenerator) {
        this.logger = logger;
        this.packageContext = packageContext;
        this.pendingIntentRequestCodeGenerator = pendingIntentRequestCodeGenerator;
        this.notificationIdGenerator = notificationIdGenerator;
        this.learnificationTextGenerator = learnificationTextGenerator;
    }

    public IdentifiedNotification learnification() {
        return learnification(learnificationTextGenerator.learnificationText());
    }

    public IdentifiedNotification learnification(LearnificationText learnificationText) {
        String learningItemPrompt = learnificationText.given;
        String subHeading = learnificationText.subHeading;
        String expectedUserResponse = learnificationText.expected;
        logger.i(LOG_TAG, "Creating a notification with title '" + learningItemPrompt + "' and text '" + subHeading + "'");

        int notificationId = notificationIdGenerator.next();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(packageContext, LearnificationNotificationChannelCreator.CHANNEL_ID)
                .setContentTitle(learningItemPrompt)
                .setContentText(subHeading)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationContentIntent()) // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_a_notification)
                .setLargeIcon(BitmapFactory.decodeResource(packageContext.getResources(), R.mipmap.ic_launcher_a_notification));
        learnificationActions(notificationId, learningItemPrompt, expectedUserResponse).forEach(notificationBuilder::addAction);
        return new IdentifiedNotification(notificationId, notificationBuilder, notificationExtras());
    }

    private PendingIntent notificationContentIntent() {
        Intent intent = new Intent(packageContext, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(packageContext, 0, intent, 0);
    }

    private Bundle notificationExtras() {
        Bundle bundle = new Bundle();
        bundle.putString(NotificationType.NOTIFICATION_TYPE_EXTRA_NAME, NotificationType.LEARNIFICATION);
        return bundle;
    }

    private List<NotificationCompat.Action> learnificationActions(int notificationId, String learningItemPrompt, String expectedUserResponse) {
        List<NotificationCompat.Action> actions = new ArrayList<>();
        actions.add(replyAction(notificationId, learningItemPrompt, expectedUserResponse));
        actions.add(pressButtonAction("Show me", learnificationResponsePendingIntent(notificationId, learningItemPrompt, expectedUserResponse, SHOW_ME)));
        actions.add(pressButtonAction("Next", learnificationResponsePendingIntent(notificationId, learningItemPrompt, expectedUserResponse, NEXT)));
        return actions;
    }

    private NotificationCompat.Action replyAction(int notificationId, String givenPrompt, String expectedUserResponse) {
        return new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                "Respond",
                learnificationResponsePendingIntent(notificationId, givenPrompt, expectedUserResponse, ANSWER))
                .addRemoteInput(new RemoteInput.Builder(REPLY_TEXT).setLabel("Respond").build())
                .build();
    }

    private NotificationCompat.Action pressButtonAction(String title, PendingIntent pendingIntent) {
        return new NotificationCompat.Action.Builder(R.drawable.android_send, title, pendingIntent).build();
    }

    private PendingIntent learnificationResponsePendingIntent(int notificationId, String learningItemPrompt, String expectedUserResponse,
                                                              LearnificationResponseType responseType) {
        Intent intent = new Intent(packageContext, LearnificationResponseService.class);
        intent.putExtra(RESPONSE_TYPE_EXTRA, responseType.name());
        intent.putExtra(EXPECTED_USER_RESPONSE_EXTRA, expectedUserResponse);
        intent.putExtra(GIVEN_PROMPT_EXTRA, learningItemPrompt);
        intent.putExtra(IdentifiedNotification.ID_EXTRA, notificationId);
        return PendingIntent.getService(
                packageContext,
                pendingIntentRequestCodeGenerator.next(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
