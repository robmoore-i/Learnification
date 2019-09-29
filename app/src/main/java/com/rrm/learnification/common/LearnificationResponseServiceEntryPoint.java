package com.rrm.learnification.common;

class LearnificationResponseServiceEntryPoint {
    private static final String LOG_TAG = "LearnificationResponseServiceEntryPoint";

    private final NotificationManager notificationManager;
    private final LearnificationScheduler learnificationScheduler;
    private final LearnificationResponseContentGenerator responseContentGenerator;
    private final AndroidLogger logger;

    LearnificationResponseServiceEntryPoint(AndroidLogger logger, NotificationManager notificationManager, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator) {
        this.logger = logger;
        this.notificationManager = notificationManager;
        this.learnificationScheduler = learnificationScheduler;
        this.responseContentGenerator = responseContentGenerator;
    }

    void onHandleIntent(LearnificationResponseIntent responseIntent) {
        if (responseIntent.isSkipped()) {
            logger.v(LOG_TAG, "learnification was skipped");
            notificationManager.cancelLatest();
        } else if (responseIntent.hasRemoteInput()) {
            String actual = responseIntent.actualUserResponse();
            String expected = responseIntent.expectedUserResponse();
            ResponseNotificationContent responseContent = responseContentGenerator.getResponseNotificationContent(expected, actual);
            logger.v(LOG_TAG, "replying with response content: " + responseContent.toString());
            notificationManager.updateLatestWithReply(responseContent);
            logger.v(LOG_TAG, "replied to learnification response of '" + actual + "' for expected value '" + expected + "'");
        } else {
            logger.e(LOG_TAG, new IllegalArgumentException("There was unexpectedly no remote input on a learnification response"));
        }

        learnificationScheduler.scheduleJob(LearnificationSchedulerService.class);
        logger.v(LOG_TAG, "scheduled next learnification");
    }
}
