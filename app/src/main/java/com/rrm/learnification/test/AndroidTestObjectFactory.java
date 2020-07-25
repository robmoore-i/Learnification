package com.rrm.learnification.test;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.dailyreport.creation.DailyReportFactory;
import com.rrm.learnification.dailyreport.creation.DailyReportTextGenerator;
import com.rrm.learnification.files.AndroidInternalStorageAdaptor;
import com.rrm.learnification.files.FileStorageAdaptor;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.learnification.creation.LearnificationFactory;
import com.rrm.learnification.learnification.publication.AndroidLearnificationScheduler;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learnificationresponse.creation.LearnificationResponseNotificationFactory;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultSqlTableClient;
import com.rrm.learnification.learningitemstorage.LearningItemSqlTableClient;
import com.rrm.learnification.learningitemstorage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ActiveNotificationReader;
import com.rrm.learnification.notification.AndroidActiveNotificationReader;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.PendingIntentIdGenerator;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;
import com.rrm.learnification.time.AndroidClock;

public class AndroidTestObjectFactory {
    private final AppCompatActivity activity;

    public AndroidTestObjectFactory(AppCompatActivity activity) {
        this.activity = activity;
    }

    private AndroidLogger logger() {
        return new AndroidLogger();
    }

    private AndroidClock clock() {
        return new AndroidClock();
    }

    public FileStorageAdaptor getFileStorageAdaptor() {
        return new AndroidInternalStorageAdaptor(logger(), activity);
    }

    public SqlLearningItemSetRecordStore getDefaultSqlLearningItemSetRecordStore() {
        return new SqlLearningItemSetRecordStore(logger(), new LearnificationAppDatabase(activity), "default");
    }

    public JobScheduler getJobScheduler() {
        return new AndroidJobScheduler(logger(), clock(), activity, getJobIdGenerator());
    }

    public JobIdGenerator getJobIdGenerator() {
        return new JobIdGenerator(logger(), getFileStorageAdaptor());
    }

    public LearnificationResponseNotificationFactory getAndroidNotificationFactory() {
        return new LearnificationResponseNotificationFactory(activity, getPendingIntentRequestCodeGenerator());
    }

    public LearnificationFactory getLearnificationFactory() {
        return new LearnificationFactory(logger(), activity, getPendingIntentRequestCodeGenerator(), getNotificationIdGenerator(),
                () -> new LearnificationText("given", "expected"));
    }

    private PendingIntentIdGenerator getPendingIntentRequestCodeGenerator() {
        return new PendingIntentIdGenerator(logger(), getFileStorageAdaptor());
    }

    private NotificationIdGenerator getNotificationIdGenerator() {
        return new NotificationIdGenerator(logger(), getFileStorageAdaptor());
    }

    public LearnificationAppDatabase getLearnificationAppDatabase() {
        return new LearnificationAppDatabase(activity);
    }

    public LearningItemSqlTableClient getLearningItemSqlTableClient() {
        return new LearningItemSqlTableClient(logger(), getLearnificationAppDatabase());
    }

    public AndroidLogger getLogger() {
        return logger();
    }

    public LearnificationScheduler getLearnificationScheduler() {
        return new AndroidLearnificationScheduler(logger(), clock(), getJobScheduler(), getScheduleConfiguration(), getActiveNotificationReader());
    }

    private ActiveNotificationReader getActiveNotificationReader() {
        return new AndroidActiveNotificationReader(getSystemNotificationManager());
    }

    private ScheduleConfiguration getScheduleConfiguration() {
        return new ScheduleConfiguration(logger(),
                getSettingsRepository());
    }

    private SettingsRepository getSettingsRepository() {
        return new SettingsRepository(logger(), getFileStorageAdaptor());
    }

    private NotificationManager getSystemNotificationManager() {
        return activity.getSystemService(NotificationManager.class);
    }

    public DailyReportFactory getDailyReportFactory() {
        return new DailyReportFactory(logger(), activity, getNotificationIdGenerator(),
                new DailyReportTextGenerator(clock(), new LearnificationResultSqlTableClient(new LearnificationAppDatabase(activity))));
    }
}
