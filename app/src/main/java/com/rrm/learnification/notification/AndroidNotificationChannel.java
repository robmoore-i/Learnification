package com.rrm.learnification.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.rrm.learnification.logger.AndroidLogger;

public abstract class AndroidNotificationChannel {
    private final AndroidLogger logger;
    private final Context androidContext;
    private final int nameResId;
    private final int descriptionResId;
    private final String channelId;

    public AndroidNotificationChannel(AndroidLogger logger, Context androidContext,
                                      int nameResId, int descriptionResId, String channelId) {
        this.logger = logger;
        this.androidContext = androidContext;
        this.nameResId = nameResId;
        this.descriptionResId = descriptionResId;
        this.channelId = channelId;
    }

    public void register() {
        logger.i(logTag(), "Creating notification channel");
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = androidContext.getString(nameResId);
            String description = androidContext.getString(descriptionResId);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            androidContext.getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        logger.i(logTag(), "Created notification channel");
    }

    private String logTag() {
        return getClass().getSimpleName();
    }
}
