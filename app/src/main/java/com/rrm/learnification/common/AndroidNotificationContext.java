package com.rrm.learnification.common;

import android.app.NotificationManager;
import android.content.Context;

import com.rrm.learnification.R;

class AndroidNotificationContext {
    private Context androidContext;

    AndroidNotificationContext(Context androidContext) {
        this.androidContext = androidContext;
    }

    String getNotificationChannelName() {
        return androidContext.getString(R.string.channel_name);
    }

    String getNotificationChannelDescription() {
        return androidContext.getString(R.string.channel_description);
    }

    NotificationManager getNotificationManager() {
        return androidContext.getSystemService(NotificationManager.class);
    }
}
