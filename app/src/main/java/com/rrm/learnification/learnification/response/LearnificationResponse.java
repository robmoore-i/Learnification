package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;

interface LearnificationResponse {
    String actualUserResponse();

    String expectedUserResponse();

    String givenPrompt();

    LearnificationResponseHandler handler(AndroidLogger logger, LearnificationScheduler learnificationScheduler,
                                          LearnificationResponseContentGenerator responseContentGenerator, LearnificationUpdater learnificationUpdater);
}
