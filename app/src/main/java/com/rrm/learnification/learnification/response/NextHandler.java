package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;

class NextHandler extends UserGuessLearnificationResponseHandler {
    private final LearnificationResponseContentGenerator responseContentGenerator;

    NextHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        super(logger, "NextHandler", learnificationScheduler, responseNotificationCorrespondent);
        this.responseContentGenerator = responseContentGenerator;
    }

    @Override
    void scheduleNextLearnification(LearnificationScheduler learnificationScheduler) {
        learnificationScheduler.scheduleImminentJob();
    }

    @Override
    String typeOfGuess() {
        return "next";
    }

    @Override
    NotificationTextContent responseNotificationContent(LearnificationResponse learnificationResponse) {
        return responseContentGenerator.getResponseNotificationTextContentForViewing(learnificationResponse);
    }
}
