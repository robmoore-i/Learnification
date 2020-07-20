package com.rrm.learnification.learnification.response.handler;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnification.response.LearnificationResponseContentGenerator;
import com.rrm.learnification.learnification.response.learnificationresponse.LearnificationResponse;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.NotificationTextContent;

public class NextHandler extends UserGuessLearnificationResponseHandler {
    private final LearnificationResponseContentGenerator responseContentGenerator;

    public NextHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler,
                       LearnificationResponseContentGenerator responseContentGenerator,
                       LearnificationUpdater learnificationUpdater, int notificationId) {
        super(logger, "NextHandler", learnificationScheduler, learnificationUpdater, notificationId);
        this.responseContentGenerator = responseContentGenerator;
    }

    @Override
    void scheduleNextLearnification(LearnificationScheduler learnificationScheduler) {
        learnificationScheduler.scheduleImminentLearnification();
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
