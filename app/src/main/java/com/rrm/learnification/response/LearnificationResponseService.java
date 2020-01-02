package com.rrm.learnification.response;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.AndroidResponseNotificationCorrespondent;
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
        ResponseNotificationCorrespondent responseNotificationCorrespondent = new AndroidResponseNotificationCorrespondent(this.getSystemService(android.app.NotificationManager.class), NotificationManagerCompat.from(this), new AndroidNotificationFactory(logger, this));
        AndroidClock clock = new AndroidClock();
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, new AndroidJobScheduler(logger, this, JobIdGenerator.getInstance(), clock), scheduleConfiguration, clock, responseNotificationCorrespondent);

        logger.v(LOG_TAG, "handling learnification response intent: " + responseIntent.toString());

        LearnificationResponseServiceEntryPoint learnificationResponseServiceEntryPoint = new LearnificationResponseServiceEntryPoint(
                logger,
                responseNotificationCorrespondent,
                learnificationScheduler,
                responseContentGenerator
        );

        learnificationResponseServiceEntryPoint.onHandleIntent(responseIntent);
        logger.v(LOG_TAG, "handled response");
    }
}
