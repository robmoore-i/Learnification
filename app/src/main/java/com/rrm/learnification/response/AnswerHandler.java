package com.rrm.learnification.response;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationScheduler;

class AnswerHandler extends UserGuessLearnificationResponseHandler {
    private final LearnificationResponseContentGenerator responseContentGenerator;

    AnswerHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler, LearnificationResponseContentGenerator responseContentGenerator, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        super(logger, "AnswerHandler", learnificationScheduler, responseNotificationCorrespondent);
        this.responseContentGenerator = responseContentGenerator;
    }

    @Override
    String typeOfGuess() {
        return "answer";
    }

    @Override
    NotificationTextContent responseNotificationContent(LearnificationResponse learnificationResponse) {
        return responseContentGenerator.getResponseNotificationTextContentForSubmittedText(learnificationResponse);
    }
}
