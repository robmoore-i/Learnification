package com.rrm.learnification.notification;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import com.rrm.learnification.R;

import java.util.ArrayList;
import java.util.List;

import static com.rrm.learnification.notification.LearnificationResponseType.ANSWER;
import static com.rrm.learnification.notification.LearnificationResponseType.NEXT;
import static com.rrm.learnification.notification.LearnificationResponseType.SHOW_ME;

public class AndroidNotificationActionFactory {
    public static final String REPLY_TEXT = "remote_input_text_reply";

    private final Context packageContext;
    private final PendingIntentRequestCodeGenerator pendingIntentRequestCodeGenerator;

    AndroidNotificationActionFactory(Context packageContext, PendingIntentRequestCodeGenerator pendingIntentRequestCodeGenerator) {
        this.packageContext = packageContext;
        this.pendingIntentRequestCodeGenerator = pendingIntentRequestCodeGenerator;
    }

    private NotificationCompat.Action replyAction(String learningItemPrompt, String expectedUserResponse) {
        return new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                "Respond",
                new AndroidPendingIntentBuilder(packageContext, expectedUserResponse, learningItemPrompt, ANSWER, pendingIntentRequestCodeGenerator).build())
                .addRemoteInput(new RemoteInput.Builder(REPLY_TEXT).setLabel("Respond").build())
                .build();
    }

    private NotificationCompat.Action showMeAction(String learningItemPrompt, String expectedUserResponse) {
        return new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                "Show me",
                new AndroidPendingIntentBuilder(packageContext, expectedUserResponse, learningItemPrompt, SHOW_ME, pendingIntentRequestCodeGenerator).build())
                .build();
    }

    private NotificationCompat.Action nextAction(String learningItemPrompt, String expectedUserResponse) {
        return new NotificationCompat.Action.Builder(
                R.drawable.android_send,
                "Next",
                new AndroidPendingIntentBuilder(packageContext, expectedUserResponse, learningItemPrompt, NEXT, pendingIntentRequestCodeGenerator).build())
                .build();
    }

    List<NotificationCompat.Action> learnificationActions(String learningItemPrompt, String expectedUserResponse) {
        List<NotificationCompat.Action> actions = new ArrayList<>();
        actions.add(replyAction(learningItemPrompt, expectedUserResponse));
        actions.add(showMeAction(learningItemPrompt, expectedUserResponse));
        actions.add(nextAction(learningItemPrompt, expectedUserResponse));
        return actions;
    }
}
