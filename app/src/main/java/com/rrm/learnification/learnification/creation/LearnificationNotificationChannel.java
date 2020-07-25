package com.rrm.learnification.learnification.creation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;

public class LearnificationNotificationChannel {
    private static final String LOG_TAG = "LearnificationNotificationChannelCreator";

    public static final String CHANNEL_ID = "learnification";

    private final AndroidLogger logger;
    private final Context androidContext;

    public LearnificationNotificationChannel(AndroidLogger logger, Context androidContext) {
        this.logger = logger;
        this.androidContext = androidContext;
    }

    public void register() {
        logger.i(LOG_TAG, "Creating notification channel");

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = androidContext.getString(R.string.channel_name);
            String description = androidContext.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            androidContext.getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

        logger.i(LOG_TAG, "Created notification channel");
    }
}
