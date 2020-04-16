package com.rrm.learnification.response;

import com.rrm.learnification.intent.ResponseIntent;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationScheduler;

import static com.rrm.learnification.notification.AndroidNotificationActionFactory.REPLY_TEXT;
import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.EXPECTED_USER_RESPONSE_EXTRA;
import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.GIVEN_PROMPT_EXTRA;
import static com.rrm.learnification.notification.AndroidPendingIntentBuilder.RESPONSE_TYPE_EXTRA;
import static com.rrm.learnification.notification.LearnificationResponseType.NEXT;
import static com.rrm.learnification.notification.LearnificationResponseType.SHOW_ME;

class AndroidIntentLearnificationResponse implements LearnificationResponse {
    private final ResponseIntent intent;

    AndroidIntentLearnificationResponse(ResponseIntent intent) {
        this.intent = intent;
    }

    @Override
    public String actualUserResponse() {
        if (!hasRemoteInput()) return null;
        CharSequence replyText = intent.getRemoteInputText(REPLY_TEXT);
        if (replyText == null) return null;
        return replyText.toString();
    }

    @Override
    public String expectedUserResponse() {
        return intent.getStringExtra(EXPECTED_USER_RESPONSE_EXTRA);
    }

    @Override
    public String givenPrompt() {
        return intent.getStringExtra(GIVEN_PROMPT_EXTRA);
    }

    @Override
    public LearnificationResponseHandler handler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        if (isShowMeResponse()) {
            return new ShowMeHandler(logger, learnificationScheduler, responseNotificationCorrespondent);
        } else if (isNextResponse()) {
            return new NextHandler(logger, learnificationScheduler, responseNotificationCorrespondent);
        } else if (hasRemoteInput()) {
            return new AnswerHandler(logger, learnificationScheduler, responseContentGenerator, responseNotificationCorrespondent);
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
        return intent.getStringExtra(RESPONSE_TYPE_EXTRA);
    }

    private boolean isShowMeResponse() {
        return SHOW_ME.name().equals(responseType());
    }

    private boolean isNextResponse() {
        return NEXT.name().equals(responseType());
    }

    private boolean hasRemoteInput() {
        return intent.hasRemoteInput();
    }
}
