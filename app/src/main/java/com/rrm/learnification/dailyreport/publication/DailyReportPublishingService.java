package com.rrm.learnification.dailyreport.publication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.dailyreport.creation.DailyReportFactory;
import com.rrm.learnification.dailyreport.creation.DailyReportTextGenerator;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultSqlTableClient;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationPublisher;
import com.rrm.learnification.notification.IdentifiedNotification;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;
import com.rrm.learnification.time.AndroidClock;

public class DailyReportPublishingService extends JobService {
    private static final String LOG_TAG = "DailyReportPublishingService";

    private final AndroidLogger logger = new AndroidLogger();
    private final AndroidClock clock = new AndroidClock();

    @Override
    public boolean onStartJob(JobParameters params) {
        publishDailyReport();
        return false;
    }

    private void publishDailyReport() {
        DailyReportFactory dailyReportFactory = new DailyReportFactory(logger, this,
                new NotificationIdGenerator(logger, new LearnificationAppDatabase(this)),
                new DailyReportTextGenerator(clock, new LearnificationResultSqlTableClient(new LearnificationAppDatabase(this))));
        IdentifiedNotification identifiedNotification = dailyReportFactory.dailyReport();
        AndroidNotificationPublisher notificationPublisher = new AndroidNotificationPublisher(logger, NotificationManagerCompat.from(this));
        notificationPublisher.publish(identifiedNotification);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        logger.i(LOG_TAG, "Job stopped");
        return false;
    }
}
