package com.rrm.learnification.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.rrm.learnification.logger.AndroidLogger;

public class LearnificationNotificationChannelCreator {
    private static final String LOG_TAG = "LearnificationNotificationChannelCreator";

    static final String CHANNEL_ID = "learnification";

    private final AndroidLogger logger;
    private final AndroidNotificationContext context;

    public LearnificationNotificationChannelCreator(AndroidLogger logger, AndroidNotificationContext context) {
        this.logger = logger;
        this.context = context;
    }

    public void createNotificationChannel() {
        logger.i(LOG_TAG, "Creating notification channel");

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getNotificationChannelName();
            String description = context.getNotificationChannelDescription();
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            context.getNotificationManager().createNotificationChannel(channel);
        }

        logger.i(LOG_TAG, "Created notification channel");
    }
}
