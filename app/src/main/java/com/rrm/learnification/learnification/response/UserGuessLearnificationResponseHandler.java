package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;

public abstract class UserGuessLearnificationResponseHandler implements LearnificationResponseHandler {
    private final AndroidLogger logger;
    private final String LOG_TAG;

    private final LearnificationScheduler learnificationScheduler;
    private final LearnificationUpdater learnificationUpdater;

    UserGuessLearnificationResponseHandler(AndroidLogger logger, String log_tag, LearnificationScheduler learnificationScheduler, LearnificationUpdater learnificationUpdater) {
        this.logger = logger;
        this.learnificationScheduler = learnificationScheduler;
        this.learnificationUpdater = learnificationUpdater;
        this.LOG_TAG = log_tag;
    }

    @Override
    public void handle(LearnificationResponse learnificationResponse) {
        logger.i(LOG_TAG, "learnification response was '" + typeOfGuess() + "'");
        NotificationTextContent responseContent = responseNotificationContent(learnificationResponse);
        logger.i(LOG_TAG, "replying with response content '" + responseContent.toString() + "'");
        learnificationUpdater.updateLatestWithReply(responseContent, learnificationResponse.givenPrompt(), learnificationResponse.expectedUserResponse());
        logger.i(LOG_TAG, "replied to learnification by showing '" + responseContent.toString() + "'");
        scheduleNextLearnification(learnificationScheduler);
    }

    abstract void scheduleNextLearnification(LearnificationScheduler learnificationScheduler);

    abstract String typeOfGuess();

    abstract NotificationTextContent responseNotificationContent(LearnificationResponse learnificationResponse);
}
