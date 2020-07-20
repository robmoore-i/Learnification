package com.rrm.learnification.learnification.response.learnificationresponse;

import com.rrm.learnification.learnification.creation.LearnificationNotificationFactory;
import com.rrm.learnification.learnification.creation.LearnificationResponseType;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnification.response.LearnificationResponseContentGenerator;
import com.rrm.learnification.learnification.response.handler.AnswerHandler;
import com.rrm.learnification.learnification.response.handler.FallthroughHandler;
import com.rrm.learnification.learnification.response.handler.LearnificationResponseHandler;
import com.rrm.learnification.learnification.response.handler.NextHandler;
import com.rrm.learnification.learnification.response.handler.ShowMeHandler;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.IdentifiedNotification;
import com.rrm.learnification.notification.NotificationResponseIntent;

public class LearnificationResponse {
    private final NotificationResponseIntent intent;

    public LearnificationResponse(NotificationResponseIntent intent) {
        this.intent = intent;
    }

    public String actualUserResponse() {
        if (!hasRemoteInput()) return null;
        CharSequence replyText = intent.getRemoteInputText(LearnificationNotificationFactory.REPLY_TEXT);
        if (replyText == null) return null;
        return replyText.toString();
    }

    public String expectedUserResponse() {
        return intent.getStringExtra(LearnificationNotificationFactory.EXPECTED_USER_RESPONSE_EXTRA);
    }

    public String givenPrompt() {
        return intent.getStringExtra(LearnificationNotificationFactory.GIVEN_PROMPT_EXTRA);
    }

    public LearnificationResponseHandler handler(
            AndroidLogger logger, LearnificationResponseContentGenerator responseContentGenerator,
            LearnificationScheduler learnificationScheduler, LearnificationUpdater learnificationUpdater) {
        int notificationId = intent.getIntExtra(IdentifiedNotification.ID_EXTRA);
        if (isShowMeResponse()) {
            return new ShowMeHandler(logger, learnificationScheduler, responseContentGenerator, learnificationUpdater, notificationId);
        } else if (isNextResponse()) {
            return new NextHandler(logger, learnificationScheduler, responseContentGenerator, learnificationUpdater, notificationId);
        } else if (hasRemoteInput()) {
            return new AnswerHandler(logger, learnificationScheduler, responseContentGenerator, learnificationUpdater, notificationId);
        } else {
            return new FallthroughHandler(logger, learnificationScheduler);
        }
    }

    @Override
    public String toString() {
        return "AndroidLearnificationResponseIntent{" +
                "responseType=" + responseType() + "," +
                "hasRemoteInput=" + hasRemoteInput() + "," +
                "actualUserResponse=" + actualUserResponse() + "," +
                "expectedUserResponse=" + expectedUserResponse() + "," +
                "givenPrompt=" + givenPrompt() + "}";
    }

    private String responseType() {
        return intent.getStringExtra(LearnificationNotificationFactory.RESPONSE_TYPE_EXTRA);
    }

    private boolean isShowMeResponse() {
        return LearnificationResponseType.SHOW_ME.name().equals(responseType());
    }

    private boolean isNextResponse() {
        return LearnificationResponseType.NEXT.name().equals(responseType());
    }

    private boolean hasRemoteInput() {
        return intent.hasRemoteInput();
    }
}
