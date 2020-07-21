package com.rrm.learnification.learnificationresponse.response;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.dailyreport.publication.DailyReportScheduler;
import com.rrm.learnification.dailyreport.publication.TestableDailyReportScheduler;
import com.rrm.learnification.files.AndroidInternalStorageAdaptor;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.learnificationresultstorage.LearnificationResult;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultSqlTableClient;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;
import com.rrm.learnification.time.AndroidClock;

public class LearnificationResultService extends IntentService {
    private static final String LOG_TAG = "LearnificationResultService";

    private final AndroidLogger logger = new AndroidLogger();
    private final AndroidClock clock = new AndroidClock();

    public LearnificationResultService() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LearnificationResultIntent learnificationResultIntent = new LearnificationResultIntent(intent);
        LearnificationResult result = learnificationResultIntent.result(clock.now());
        logger.u(LOG_TAG, "user reported a result '" + result.toString() + "'");
        // Cancel notification
        NotificationCanceller notificationCanceller = new NotificationCanceller(NotificationManagerCompat.from(this));
        notificationCanceller.cancel(learnificationResultIntent.notificationId());
        // Persist the event data
        LearnificationResultSqlTableClient learnificationResultSqlTableClient = new LearnificationResultSqlTableClient(new LearnificationAppDatabase(this));
        learnificationResultSqlTableClient.write(result);
        // Schedule daily report notification
        AndroidInternalStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        DailyReportScheduler dailyReportScheduler = new DailyReportScheduler(new TestableDailyReportScheduler(logger, clock,
                new AndroidJobScheduler(logger, clock, this,
                        new JobIdGenerator(logger, fileStorageAdaptor)),
                new ScheduleConfiguration(logger, new SettingsRepository(logger, fileStorageAdaptor))));
        dailyReportScheduler.scheduleJob();
        logger.i(LOG_TAG, "handled result of learnification");
    }
}
