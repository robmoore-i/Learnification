package com.rrm.learnification.response;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.intent.AndroidResponseIntent;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.AndroidResponseNotificationCorrespondent;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.PendingIntentIdGenerator;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.time.AndroidClock;

public class LearnificationResponseService extends IntentService {
    private static final String LOG_TAG = "LearnificationResponseService";

    private final AndroidLogger logger = new AndroidLogger();
    private final AndroidClock clock = new AndroidClock();

    public LearnificationResponseService() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AndroidIntentLearnificationResponse learnificationResponse = new AndroidIntentLearnificationResponse(new AndroidResponseIntent(intent));
        logger.i(LOG_TAG, "handling learnification response: " + learnificationResponse.toString());

        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, new SettingsRepository(logger, fileStorageAdaptor));
        ResponseNotificationCorrespondent responseNotificationCorrespondent = new AndroidResponseNotificationCorrespondent(
                logger,
                this.getSystemService(android.app.NotificationManager.class),
                NotificationManagerCompat.from(this),
                new AndroidNotificationFactory(logger, this, PendingIntentIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor)),
                NotificationIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor));
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, clock,
                new AndroidJobScheduler(logger, clock, this, JobIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor)),
                scheduleConfiguration,
                responseNotificationCorrespondent);

        learnificationResponse
                .handler(logger, learnificationScheduler, new LearnificationResponseContentGenerator(scheduleConfiguration), responseNotificationCorrespondent)
                .handle(learnificationResponse);
        logger.i(LOG_TAG, "handled response");
    }
}
