package com.rrm.learnification.learnificationresponse.response;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.logger.AndroidLogger;

public class LearnificationResultService extends IntentService {
    private static final String LOG_TAG = "LearnificationResultService";

    private final AndroidLogger logger = new AndroidLogger();

    public LearnificationResultService() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LearnificationResultIntent learnificationResultIntent = new LearnificationResultIntent(intent);
        logger.u(LOG_TAG, "user reported that they are " + (learnificationResultIntent.correctResponse() ? "" : "not ") + "correct");
        NotificationCanceller notificationCanceller = new NotificationCanceller(NotificationManagerCompat.from(this));
        notificationCanceller.cancel(learnificationResultIntent.notificationId());
        logger.i(LOG_TAG, "handled result of learnification");
    }
}
