package com.rrm.learnification.learnification.response;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.files.AndroidInternalStorageAdaptor;
import com.rrm.learnification.files.FileStorageAdaptor;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.learnification.publication.AndroidLearnificationScheduler;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnificationresponse.creation.LearnificationResponseNotificationFactory;
import com.rrm.learnification.learnificationresponse.publication.AndroidLearnificationUpdater;
import com.rrm.learnification.learnificationresponse.publication.LearnificationUpdater;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidActiveNotificationReader;
import com.rrm.learnification.notification.PendingIntentIdGenerator;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;
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
        LearnificationResponse learnificationResponse = new LearnificationResponse(new LearnificationResponseIntent(intent));

        logger.i(LOG_TAG, "handling learnification response: " + learnificationResponse.toString());

        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, new SettingsRepository(logger, fileStorageAdaptor));
        LearnificationAppDatabase database = new LearnificationAppDatabase(this);
        LearnificationUpdater learnificationUpdater = new AndroidLearnificationUpdater(
                logger,
                NotificationManagerCompat.from(this),
                new LearnificationResponseNotificationFactory(this, new PendingIntentIdGenerator(logger, database)));
        LearnificationScheduler learnificationScheduler = new AndroidLearnificationScheduler(logger, clock,
                new AndroidJobScheduler(logger, clock, this, new JobIdGenerator(logger, database)),
                scheduleConfiguration,
                new AndroidActiveNotificationReader(this.getSystemService(android.app.NotificationManager.class)));

        learnificationResponse
                .handler(logger, new LearnificationResponseContentGenerator(scheduleConfiguration), learnificationScheduler, learnificationUpdater)
                .handle(learnificationResponse);

        logger.i(LOG_TAG, "handled response");
    }
}
