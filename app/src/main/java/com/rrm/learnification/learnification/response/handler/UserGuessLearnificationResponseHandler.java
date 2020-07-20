package com.rrm.learnification.learnification.response.handler;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnification.response.learnificationresponse.LearnificationResponse;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.NotificationTextContent;

public abstract class UserGuessLearnificationResponseHandler implements LearnificationResponseHandler {
    private final AndroidLogger logger;
    private final String LOG_TAG;

    private final LearnificationScheduler learnificationScheduler;
    private final LearnificationUpdater learnificationUpdater;
    private int notificationId;

    UserGuessLearnificationResponseHandler(AndroidLogger logger, String log_tag, LearnificationScheduler learnificationScheduler,
                                           LearnificationUpdater learnificationUpdater, int notificationId) {
        this.logger = logger;
        this.learnificationScheduler = learnificationScheduler;
        this.learnificationUpdater = learnificationUpdater;
        this.LOG_TAG = log_tag;
        this.notificationId = notificationId;
    }

    @Override
    public void handle(LearnificationResponse learnificationResponse) {
        logger.i(LOG_TAG, "learnification response was '" + typeOfGuess() + "'");
        NotificationTextContent responseContent = responseNotificationContent(learnificationResponse);
        logger.i(LOG_TAG, "replying with response content '" + responseContent.toString() + "'");
        learnificationUpdater.updateWithResponse(responseContent, learnificationResponse.givenPrompt(),
                learnificationResponse.expectedUserResponse(), notificationId);
        logger.i(LOG_TAG, "replied to learnification by showing '" + responseContent.toString() + "'");
        scheduleNextLearnification(learnificationScheduler);
    }

    abstract void scheduleNextLearnification(LearnificationScheduler learnificationScheduler);

    abstract String typeOfGuess();

    abstract NotificationTextContent responseNotificationContent(LearnificationResponse learnificationResponse);
}
