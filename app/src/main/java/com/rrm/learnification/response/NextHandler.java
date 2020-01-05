package com.rrm.learnification.response;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class NextHandler implements LearnificationResponseHandler {
    private static final String LOG_TAG = "NextHandler";
    private AndroidLogger logger;
    private ResponseNotificationCorrespondent responseNotificationCorrespondent;
    private LearnificationScheduler learnificationScheduler;

    NextHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        this.logger = logger;
        this.responseNotificationCorrespondent = responseNotificationCorrespondent;
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public void handle(LearnificationResponseIntent responseIntent) {
        logger.v(LOG_TAG, "learnification response was 'next'");
        String given = responseIntent.givenPrompt();
        String expected = responseIntent.expectedUserResponse();
        NotificationTextContent responseContent = new NotificationTextContent(given + " -> " + expected, "Showing answer for last learnification");
        logger.v(LOG_TAG, "replying with response content: " + responseContent.toString());
        responseNotificationCorrespondent.updateLatestWithReply(responseContent);
        logger.v(LOG_TAG, "replied to learnification by showing '" + given + " -> " + expected + "'");
        learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);
    }
}
