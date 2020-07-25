package com.rrm.learnification.learnificationresponse.creation;

import android.content.Context;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationChannel;

public class LearnificationResponseNotificationChannel extends AndroidNotificationChannel {
    static final String CHANNEL_ID = "learnification-response";

    public LearnificationResponseNotificationChannel(AndroidLogger logger, Context androidContext) {
        super(logger, androidContext,
                R.string.learnification_response_channel_name, R.string.learnification_response_channel_description,
                CHANNEL_ID);
    }
}
