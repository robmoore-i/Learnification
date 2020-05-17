package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;

public abstract class UserGuessLearnificationResponseHandler implements LearnificationResponseHandler {
    private final AndroidLogger logger;
    private final String LOG_TAG;

    private final LearnificationScheduler learnificationScheduler;
    private final ResponseNotificationCorrespondent responseNotificationCorrespondent;

    UserGuessLearnificationResponseHandler(AndroidLogger logger, String log_tag, LearnificationScheduler learnificationScheduler, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        this.logger = logger;
        this.learnificationScheduler = learnificationScheduler;
        this.responseNotificationCorrespondent = responseNotificationCorrespondent;
        this.LOG_TAG = log_tag;
    }

    @Override
    public void handle(LearnificationResponse learnificationResponse) {
        logger.i(LOG_TAG, "learnification response was '" + typeOfGuess() + "'");
        NotificationTextContent responseContent = responseNotificationContent(learnificationResponse);
        logger.i(LOG_TAG, "replying with response content '" + responseContent.toString() + "'");
        responseNotificationCorrespondent.updateLatestWithReply(responseContent, learnificationResponse.givenPrompt(), learnificationResponse.expectedUserResponse());
        logger.i(LOG_TAG, "replied to learnification by showing '" + responseContent.toString() + "'");
        scheduleNextLearnification(learnificationScheduler);
    }

    abstract void scheduleNextLearnification(LearnificationScheduler learnificationScheduler);

    abstract String typeOfGuess();

    abstract NotificationTextContent responseNotificationContent(LearnificationResponse learnificationResponse);
}
