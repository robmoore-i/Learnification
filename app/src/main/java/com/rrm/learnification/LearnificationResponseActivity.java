package com.rrm.learnification;

import android.app.Notification;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;

public class LearnificationResponseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

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

        scheduleJob(this);

        finish();
    }

    // schedule the start of the service every 10-20 seconds
    public void scheduleJob(Context context) {
        int fiveMinutes = 300000;
        int sixMinutes = 360000;

        int tenSeconds = 10000;
        int twentySeconds = 20000;

        JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(context, LearnificationSchedulerService.class))
                .setMinimumLatency(tenSeconds)
                .setOverrideDeadline(twentySeconds)
                .setRequiresCharging(false);
        context.getSystemService(JobScheduler.class).schedule(builder.build());
    }
}
