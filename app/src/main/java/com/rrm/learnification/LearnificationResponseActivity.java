package com.rrm.learnification;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;

public class LearnificationResponseActivity extends AppCompatActivity {
    private final AndroidLogger logger = new AndroidLogger();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, new SettingsRepository(logger, new AndroidInternalStorageAdaptor(logger, this)));
        LearnificationResponseTextGenerator learnificationResponseTextGenerator = new LearnificationResponseTextGenerator(scheduleConfiguration);

        Bundle remoteInput = RemoteInput.getResultsFromIntent(this.getIntent());
        if (remoteInput != null) {
            @SuppressWarnings("ConstantConditions")
            String inputString = remoteInput.getCharSequence(AndroidNotificationFactory.REPLY_TEXT).toString();
            String replyText = learnificationResponseTextGenerator.getReplyText(inputString);
            AndroidNotificationFactory androidNotificationFactory = new AndroidNotificationFactory(logger, this);
            Notification replyNotification = androidNotificationFactory.buildResponseNotification("Result", replyText);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NotificationIdGenerator.getInstance().lastNotificationId(), replyNotification);
        }

        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, new AndroidJobSchedulerContext(this), scheduleConfiguration);
        learnificationScheduler.scheduleJob();

        finish();
    }
}
