package com.rrm.learnification.dailyreport.creation;

import android.content.Context;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationChannel;

public class DailyReportNotificationChannel extends AndroidNotificationChannel {
    static final String CHANNEL_ID = "daily-report";

    public DailyReportNotificationChannel(AndroidLogger logger, Context androidContext) {
        super(logger, androidContext,
                R.string.daily_report_channel_name, R.string.daily_report_channel_description,
                CHANNEL_ID);
    }
}
