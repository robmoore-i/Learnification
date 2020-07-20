package com.rrm.learnification.learnification.response.handler;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnification.response.LearnificationResponse;
import com.rrm.learnification.logger.AndroidLogger;

public class FallthroughHandler implements LearnificationResponseHandler {
    private static final String LOG_TAG = "FallthroughHandler";
    private final AndroidLogger logger;
    private final LearnificationScheduler learnificationScheduler;

    public FallthroughHandler(AndroidLogger logger, LearnificationScheduler learnificationScheduler) {
        this.logger = logger;
        this.learnificationScheduler = learnificationScheduler;
    }

    @Override
    public void handle(LearnificationResponse learnificationResponse) {
        logger.e(LOG_TAG, new IllegalArgumentException("There was unexpectedly no remote input on a learnification response"));
        learnificationScheduler.scheduleLearnification();
    }
}
