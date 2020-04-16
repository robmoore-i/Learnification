package com.rrm.learnification.response;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class AnswerHandler implements LearnificationResponseHandler {
    private static final String LOG_TAG = "AnswerHandler";
    private final AndroidLogger logger;
    private final LearnificationResponseContentGenerator responseContentGenerator;
    private final ResponseNotificationCorrespondent responseNotificationCorrespondent;
    private final LearnificationScheduler learnificationScheduler;

    AnswerHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        this.logger = logger;
        this.responseContentGenerator = responseContentGenerator;
        this.responseNotificationCorrespondent = responseNotificationCorrespondent;
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public void handle(LearnificationResponse learnificationResponse) {
        logger.i(LOG_TAG, "learnification response was 'answer'");
        String actual = learnificationResponse.actualUserResponse();
        String expected = learnificationResponse.expectedUserResponse();
        NotificationTextContent responseContent = responseContentGenerator.getResponseNotificationTextContent(expected, actual);
        logger.i(LOG_TAG, "replying with response content: " + responseContent.toString());
        responseNotificationCorrespondent.updateLatestWithReply(responseContent);
        logger.i(LOG_TAG, "replied to learnification response of '" + actual + "' for expected value '" + expected + "'");
        learnificationScheduler.scheduleJob(LearnificationPublishingService.class);
    }
}
