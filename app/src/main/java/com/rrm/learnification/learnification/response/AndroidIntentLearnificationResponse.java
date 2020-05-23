package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.creation.LearnificationNotificationFactory;
import com.rrm.learnification.learnification.creation.LearnificationResponseType;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.IdentifiedNotification;

class AndroidIntentLearnificationResponse implements LearnificationResponse {
    private final ResponseIntent intent;

    AndroidIntentLearnificationResponse(ResponseIntent intent) {
        this.intent = intent;
    }

    @Override
    public String actualUserResponse() {
        if (!hasRemoteInput()) return null;
        CharSequence replyText = intent.getRemoteInputText(LearnificationNotificationFactory.REPLY_TEXT);
        if (replyText == null) return null;
        return replyText.toString();
    }

    @Override
    public String expectedUserResponse() {
        return intent.getStringExtra(LearnificationNotificationFactory.EXPECTED_USER_RESPONSE_EXTRA);
    }

    @Override
    public String givenPrompt() {
        return intent.getStringExtra(LearnificationNotificationFactory.GIVEN_PROMPT_EXTRA);
    }

    @Override
    public LearnificationResponseHandler handler(AndroidLogger logger, LearnificationScheduler learnificationScheduler,
                                                 LearnificationResponseContentGenerator responseContentGenerator, LearnificationUpdater learnificationUpdater) {

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
