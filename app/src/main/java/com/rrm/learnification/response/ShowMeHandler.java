package com.rrm.learnification.response;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

class ShowMeHandler extends UserGuessLearnificationResponseHandler {
    private final LearnificationResponseContentGenerator responseContentGenerator;

    ShowMeHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        super(logger, "ShowMeHandler", learnificationScheduler, responseNotificationCorrespondent);
        this.responseContentGenerator = responseContentGenerator;
    }

    @Override
    void scheduleNextLearnification(LearnificationScheduler learnificationScheduler) {
        learnificationScheduler.scheduleJob(LearnificationPublishingService.class);
    }

    @Override
    String typeOfGuess() {
        return "show-me";
    }

    @Override
    NotificationTextContent responseNotificationContent(LearnificationResponse learnificationResponse) {
        return responseContentGenerator.getResponseNotificationTextContentForViewing(learnificationResponse);
    }
}
