package com.rrm.learnification;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

import java.time.Clock;

public class LearnificationResponseService extends IntentService {
    private static final String LOG_TAG = "LearnificationResponseService";

    private final AndroidLogger logger = new AndroidLogger();

    public LearnificationResponseService() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        logger.v(LOG_TAG, "entered learnification response handler");
        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, new SettingsRepository(logger, fileStorageAdaptor));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        boolean skipped = intent.getBooleanExtra(AndroidNotificationFactory.SKIPPED_FLAG_EXTRA, false);
        if (skipped) {
            cancelLearnification(notificationManager);
        } else {
            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
            if (remoteInput != null) {
                LearnificationResponseContentGenerator responseContentGenerator = new LearnificationResponseContentGenerator(scheduleConfiguration);
                updateLearnificationWithResult(notificationManager, intent, remoteInput, responseContentGenerator);
            } else {
                logger.e(LOG_TAG, new IllegalArgumentException("remoteInput was null"));
            }
        }
        scheduleNextLearnification(fileStorageAdaptor, scheduleConfiguration);
    }

    private void scheduleNextLearnification(FileStorageAdaptor fileStorageAdaptor, ScheduleConfiguration scheduleConfiguration) {
        ScheduleLog scheduleLog = new FromFileScheduleLog(logger, fileStorageAdaptor, Clock.systemDefaultZone());
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, new AndroidScheduler(this, JobIdGenerator.getInstance()), scheduleConfiguration, scheduleLog, new AndroidClock());
        learnificationScheduler.scheduleJob(LearnificationSchedulerService.class);
        logger.v(LOG_TAG, "scheduled next learnification");
    }

    private void cancelLearnification(NotificationManagerCompat notificationManager) {
        logger.v(LOG_TAG, "learnification was skipped");
        notificationManager.cancel(NotificationIdGenerator.getInstance().lastNotificationId());
    }

    private void updateLearnificationWithResult(NotificationManagerCompat notificationManager, Intent intent, Bundle remoteInput, LearnificationResponseContentGenerator responseContentGenerator) {
        @SuppressWarnings("ConstantConditions")
        String actual = remoteInput.getCharSequence(AndroidNotificationFactory.REPLY_TEXT).toString();
        String expected = intent.getStringExtra(AndroidNotificationFactory.EXPECTED_USER_RESPONSE_EXTRA);
        logger.v(LOG_TAG, "learnification got a response '" + actual + "' with expected value '" + expected + "'");

        ResponseNotificationContent responseNotificationContent = responseContentGenerator.getResponseNotificationContent(expected, actual);
        AndroidNotificationFactory androidNotificationFactory = new AndroidNotificationFactory(logger, this);
        Notification replyNotification = androidNotificationFactory.buildResponseNotification(responseNotificationContent);
        notificationManager.notify(NotificationIdGenerator.getInstance().lastNotificationId(), replyNotification);
    }
}
