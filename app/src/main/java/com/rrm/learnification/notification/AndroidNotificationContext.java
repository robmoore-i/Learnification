package com.rrm.learnification.notification;

import android.app.NotificationManager;
import android.content.Context;

import com.rrm.learnification.R;

public class AndroidNotificationContext {
    private final Context androidContext;

    public AndroidNotificationContext(Context androidContext) {
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
