package com.rrm.learnification.response;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class LearnificationResponseServiceEntryPoint {
    private static final String LOG_TAG = "LearnificationResponseServiceEntryPoint";

    private final ResponseNotificationCorrespondent responseNotificationCorrespondent;
    private final LearnificationScheduler learnificationScheduler;
    private final LearnificationResponseContentGenerator responseContentGenerator;
    private final AndroidLogger logger;

    LearnificationResponseServiceEntryPoint(AndroidLogger logger, ResponseNotificationCorrespondent responseNotificationCorrespondent, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator) {
        this.logger = logger;
        this.responseNotificationCorrespondent = responseNotificationCorrespondent;
        this.learnificationScheduler = learnificationScheduler;
        this.responseContentGenerator = responseContentGenerator;
    }

    void onHandleIntent(LearnificationResponseIntent responseIntent) {
        if (responseIntent.isShowMeResponse()) {
            logger.v(LOG_TAG, "learnification response was 'show-me'");
            String given = responseIntent.givenPrompt();
            String expected = responseIntent.expectedUserResponse();
            NotificationTextContent responseContent = new NotificationTextContent(given + " -> " + expected, "Showing answer for last learnification");
            logger.v(LOG_TAG, "replying with response content: " + responseContent.toString());
            responseNotificationCorrespondent.updateLatestWithReply(responseContent);
            logger.v(LOG_TAG, "replied to learnification by showing '" + given + " -> " + expected + "'");
        } else if (responseIntent.hasRemoteInput()) {
            String actual = responseIntent.actualUserResponse();
            String expected = responseIntent.expectedUserResponse();
            NotificationTextContent responseContent = responseContentGenerator.getResponseNotificationTextContent(expected, actual);
            logger.v(LOG_TAG, "replying with response content: " + responseContent.toString());
            responseNotificationCorrespondent.updateLatestWithReply(responseContent);
            logger.v(LOG_TAG, "replied to learnification response of '" + actual + "' for expected value '" + expected + "'");
        } else {
            logger.e(LOG_TAG, new IllegalArgumentException("There was unexpectedly no remote input on a learnification response"));
        }

        learnificationScheduler.scheduleJob(LearnificationPublishingService.class);
    }
}