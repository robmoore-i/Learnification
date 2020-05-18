package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;

class ShowMeHandler extends UserGuessLearnificationResponseHandler {
    private final LearnificationResponseContentGenerator responseContentGenerator;

    ShowMeHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        super(logger, "ShowMeHandler", learnificationScheduler, responseNotificationCorrespondent);
        this.responseContentGenerator = responseContentGenerator;
    }

    @Override
    void scheduleNextLearnification(LearnificationScheduler learnificationScheduler) {
        learnificationScheduler.scheduleLearnification();
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
