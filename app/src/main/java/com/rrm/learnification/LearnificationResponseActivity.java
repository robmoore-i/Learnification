package com.rrm.learnification;

import android.app.Notification;
import android.content.Intent;
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

        Intent intent = this.getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            @SuppressWarnings("ConstantConditions")
            String actual = remoteInput.getCharSequence(AndroidNotificationFactory.REPLY_TEXT).toString();
            String expected = intent.getStringExtra(AndroidNotificationFactory.EXPECTED_USER_RESPONSE_EXTRA);
            String replyText = learnificationResponseTextGenerator.getReplyText(expected, actual);
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
