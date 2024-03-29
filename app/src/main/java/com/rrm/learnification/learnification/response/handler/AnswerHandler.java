package com.rrm.learnification.learnification.response.handler;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnification.response.LearnificationResponse;
import com.rrm.learnification.learnification.response.LearnificationResponseContentGenerator;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.NotificationTextContent;

public class AnswerHandler extends UserGuessLearnificationResponseHandler {
    private final LearnificationResponseContentGenerator responseContentGenerator;

    public AnswerHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler,
                         LearnificationResponseContentGenerator responseContentGenerator,
                         LearnificationUpdater learnificationUpdater, int notificationId) {
        super(logger, "AnswerHandler", learnificationScheduler, learnificationUpdater, notificationId);
        this.responseContentGenerator = responseContentGenerator;
    }

    @Override
    void scheduleNextLearnification(LearnificationScheduler learnificationScheduler) {
        learnificationScheduler.scheduleLearnification();
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
