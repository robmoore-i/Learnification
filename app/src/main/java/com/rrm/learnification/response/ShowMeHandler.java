package com.rrm.learnification.response;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class ShowMeHandler implements LearnificationResponseHandler {
    private static final String LOG_TAG = "ShowMeHandler";
    private AndroidLogger logger;
    private ResponseNotificationCorrespondent responseNotificationCorrespondent;
    private LearnificationScheduler learnificationScheduler;

    ShowMeHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        this.logger = logger;
        this.responseNotificationCorrespondent = responseNotificationCorrespondent;
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public void handle(LearnificationResponseIntent responseIntent) {
        logger.i(LOG_TAG, "learnification response was 'show-me'");
        String given = responseIntent.givenPrompt();
        String expected = responseIntent.expectedUserResponse();
        NotificationTextContent responseContent = new NotificationTextContent(given + " -> " + expected, "Showing answer for last learnification");
        logger.i(LOG_TAG, "replying with response content: " + responseContent.toString());
        responseNotificationCorrespondent.updateLatestWithReply(responseContent);
        logger.i(LOG_TAG, "replied to learnification by showing '" + given + " -> " + expected + "'");
        learnificationScheduler.scheduleJob(LearnificationPublishingService.class);
    }
}
