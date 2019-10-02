package com.rrm.learnification.learnification;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.common.AndroidClock;
import com.rrm.learnification.common.AndroidInternalStorageAdaptor;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.FileStorageAdaptor;
import com.rrm.learnification.settings.SettingsRepository;

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
        AndroidLearnificationResponseIntent responseIntent = new AndroidLearnificationResponseIntent(intent);
        LearnificationResponseContentGenerator responseContentGenerator = new LearnificationResponseContentGenerator(scheduleConfiguration);
        NotificationManager notificationManager = new AndroidNotificationManager(NotificationManagerCompat.from(this), new AndroidNotificationFactory(logger, this));
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, new AndroidJobScheduler(this, JobIdGenerator.getInstance()), scheduleConfiguration, new FromFileScheduleLog(logger, fileStorageAdaptor, Clock.systemDefaultZone()), new AndroidClock());

        logger.v(LOG_TAG, "handling learnification response intent: " + responseIntent.toString());

        LearnificationResponseServiceEntryPoint learnificationResponseServiceEntryPoint = new LearnificationResponseServiceEntryPoint(
                logger,
                notificationManager,
                learnificationScheduler,
                responseContentGenerator
        );

        learnificationResponseServiceEntryPoint.onHandleIntent(responseIntent);
    }
}
