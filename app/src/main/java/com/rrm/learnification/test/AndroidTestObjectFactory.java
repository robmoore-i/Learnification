package com.rrm.learnification.test;

import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.files.AndroidInternalStorageAdaptor;
import com.rrm.learnification.files.FileStorageAdaptor;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.learnification.creation.LearnificationNotificationFactory;
import com.rrm.learnification.learnificationresponse.creation.LearnificationResponseNotificationFactory;
import com.rrm.learnification.learningitemstorage.LearningItemSqlTableClient;
import com.rrm.learnification.learningitemstorage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.PendingIntentIdGenerator;
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
        return new LearnificationResponseNotificationFactory(activity, new PendingIntentIdGenerator(logger(), getFileStorageAdaptor()));
    }

    public LearnificationNotificationFactory getLearnificationNotificationFactory() {
        PendingIntentIdGenerator pendingIntentRequestCodeGenerator = new PendingIntentIdGenerator(logger(), getFileStorageAdaptor());
        NotificationIdGenerator notificationIdGenerator = new NotificationIdGenerator(logger(), getFileStorageAdaptor());
        return new LearnificationNotificationFactory(logger(), activity, pendingIntentRequestCodeGenerator, notificationIdGenerator);
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
}
