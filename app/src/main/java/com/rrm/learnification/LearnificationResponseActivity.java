package com.rrm.learnification;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;

public class LearnificationResponseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateLearnificationWithResponse();
        scheduleNextLearnification();
        finish();
    }

    private void scheduleNextLearnification() {
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(new AndroidJobSchedulerContext(this));
        learnificationScheduler.scheduleJob(5000, 10000);
    }

    private void updateLearnificationWithResponse() {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(this.getIntent());
        if (remoteInput != null) {
            @SuppressWarnings("ConstantConditions")
            String inputString = remoteInput.getCharSequence(AndroidLearnificationFactory.REPLY_TEXT).toString();

            Notification repliedNotification = new Notification.Builder(this, MainActivity.CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentText("Response received: " + inputString)
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NotificationIdGenerator.getInstance().lastNotificationId(), repliedNotification);
        }
    }
}
