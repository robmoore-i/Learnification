package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;

interface LearnificationResponse {
    String actualUserResponse();

    String expectedUserResponse();

    String givenPrompt();

    LearnificationResponseHandler handler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator, ResponseNotificationCorrespondent responseNotificationCorrespondent);
}
