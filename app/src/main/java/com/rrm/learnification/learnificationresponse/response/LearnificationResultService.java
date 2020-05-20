package com.rrm.learnification.learnificationresponse.response;

import android.app.IntentService;
import android.content.Intent;

import com.rrm.learnification.logger.AndroidLogger;

public class LearnificationResultService extends IntentService {
    private static final String LOG_TAG = "LearnificationResultService";

    private final AndroidLogger logger = new AndroidLogger();

    public LearnificationResultService() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        logger.i(LOG_TAG, "handled result of learnification");
    }
}
