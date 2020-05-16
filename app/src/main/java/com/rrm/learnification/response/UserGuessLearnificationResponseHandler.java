package com.rrm.learnification.response;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

public abstract class UserGuessLearnificationResponseHandler implements LearnificationResponseHandler {
    private final AndroidLogger logger;
    private final String LOG_TAG;

    private final ResponseNotificationCorrespondent responseNotificationCorrespondent;
    private final LearnificationScheduler learnificationScheduler;

    UserGuessLearnificationResponseHandler(AndroidLogger logger, String log_tag, LearnificationScheduler learnificationScheduler, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        this.logger = logger;
        this.responseNotificationCorrespondent = responseNotificationCorrespondent;
        this.learnificationScheduler = learnificationScheduler;
        this.LOG_TAG = log_tag;
    }

    @Override
    public void handle(LearnificationResponse learnificationResponse) {
        logger.i(LOG_TAG, "learnification response was '" + typeOfGuess() + "'");
        NotificationTextContent responseContent = responseNotificationContent(learnificationResponse);
        logger.i(LOG_TAG, "replying with response content '" + responseContent.toString() + "'");
        responseNotificationCorrespondent.updateLatestWithReply(responseContent, learnificationResponse.givenPrompt(), learnificationResponse.expectedUserResponse());
        logger.i(LOG_TAG, "replied to learnification by showing '" + responseContent.toString() + "'");
        learnificationScheduler.scheduleJob(LearnificationPublishingService.class);
    }

    abstract String typeOfGuess();

    abstract NotificationTextContent responseNotificationContent(LearnificationResponse learnificationResponse);
}
