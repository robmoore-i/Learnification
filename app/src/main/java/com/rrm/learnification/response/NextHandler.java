package com.rrm.learnification.response;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class NextHandler implements LearnificationResponseHandler {
    private static final String LOG_TAG = "NextHandler";
    private final AndroidLogger logger;
    private final ResponseNotificationCorrespondent responseNotificationCorrespondent;
    private final LearnificationScheduler learnificationScheduler;

    NextHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        this.logger = logger;
        this.responseNotificationCorrespondent = responseNotificationCorrespondent;
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public void handle(LearnificationResponse learnificationResponse) {
        logger.i(LOG_TAG, "learnification response was 'next'");
        String given = learnificationResponse.givenPrompt();
        String expected = learnificationResponse.expectedUserResponse();
        NotificationTextContent responseContent = new NotificationTextContent(given + " -> " + expected, "Showing answer for last learnification");
        logger.i(LOG_TAG, "replying with response content: " + responseContent.toString());
        responseNotificationCorrespondent.updateLatestWithReply(responseContent);
        logger.i(LOG_TAG, "replied to learnification by showing '" + given + " -> " + expected + "'");
        learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);
    }
}
