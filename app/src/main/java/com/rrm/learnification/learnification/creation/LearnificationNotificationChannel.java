package com.rrm.learnification.learnification.creation;

import android.content.Context;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationChannel;

public class LearnificationNotificationChannel extends AndroidNotificationChannel {
    static final String CHANNEL_ID = "learnification";

    public LearnificationNotificationChannel(AndroidLogger logger, Context androidContext) {
        super(logger, androidContext,
                R.string.learnification_channel_name, R.string.learnification_channel_description,
                CHANNEL_ID);
    }
}
