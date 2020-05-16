package com.rrm.learnification.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.R;

import java.util.ArrayList;
import java.util.List;

import static com.rrm.learnification.notification.LearnificationResponseType.ANSWER;
import static com.rrm.learnification.notification.LearnificationResponseType.I_KNEW_IT;
import static com.rrm.learnification.notification.LearnificationResponseType.NEXT;
import static com.rrm.learnification.notification.LearnificationResponseType.SHOW_ME;

public class AndroidNotificationActionFactory {
    public static final String REPLY_TEXT = "remote_input_text_reply";

    private final Context packageContext;
    private final PendingIntentIdGenerator pendingIntentRequestCodeGenerator;

    AndroidNotificationActionFactory(Context packageContext, PendingIntentIdGenerator pendingIntentRequestCodeGenerator) {
        this.packageContext = packageContext;
        this.pendingIntentRequestCodeGenerator = pendingIntentRequestCodeGenerator;
    }

    private NotificationCompat.Action replyAction(String givenPrompt, String expectedUserResponse) {
        return new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                "Respond",
                learnificationResponsePendingIntent(givenPrompt, expectedUserResponse, ANSWER))
                .addRemoteInput(new RemoteInput.Builder(REPLY_TEXT).setLabel("Respond").build())
                .build();
    }

    private NotificationCompat.Action pressButtonAction(String title, PendingIntent pendingIntent) {
        return new NotificationCompat.Action.Builder(R.drawable.android_send, title, pendingIntent).build();
    }

    private PendingIntent learnificationResponsePendingIntent(String learningItemPrompt, String expectedUserResponse, LearnificationResponseType responseType) {
        return new AndroidLearnificationResponsePendingIntentBuilder(packageContext, learningItemPrompt, expectedUserResponse, responseType, pendingIntentRequestCodeGenerator).build();
    }

    List<NotificationCompat.Action> learnificationActions(String learningItemPrompt, String expectedUserResponse) {
        List<NotificationCompat.Action> actions = new ArrayList<>();
        actions.add(replyAction(learningItemPrompt, expectedUserResponse));
        actions.add(pressButtonAction("Show me", learnificationResponsePendingIntent(learningItemPrompt, expectedUserResponse, SHOW_ME)));
        actions.add(pressButtonAction("Next", learnificationResponsePendingIntent(learningItemPrompt, expectedUserResponse, NEXT)));
        return actions;
    }

    List<NotificationCompat.Action> learnificationResponseActions(String givenPrompt, String expectedUserResponse) {
        List<NotificationCompat.Action> actions = new ArrayList<>();
        PendingIntent pendingIntent = learnificationResponsePendingIntent(givenPrompt, expectedUserResponse, I_KNEW_IT);
        actions.add(pressButtonAction("My answer was ✅", pendingIntent));
        actions.add(pressButtonAction("My answer was ❌", pendingIntent));
        return actions;
    }
}
