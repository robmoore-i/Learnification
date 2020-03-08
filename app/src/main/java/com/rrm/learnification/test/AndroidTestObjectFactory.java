package com.rrm.learnification.test;

import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.PendingIntentRequestCodeGenerator;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSetSqlRecordStore;
import com.rrm.learnification.storage.LearningItemUpdateBroker;
import com.rrm.learnification.storage.PersistentItemStore;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.storage.SqlPersistentLearningItemStore;
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

    public PersistentItemStore<LearningItem> getLearningItemStorage() {
        AndroidLogger logger = logger();
        return new SqlPersistentLearningItemStore(logger, new LearningItemSetSqlRecordStore(logger, new LearnificationAppDatabase(activity), "default"));
    }

    public ItemRepository<LearningItem> getLearningItemRepository() {
        return new PersistentLearningItemRepository(logger(), getLearningItemStorage(), new LearningItemUpdateBroker());
    }

    public JobScheduler getJobScheduler() {
        return new AndroidJobScheduler(logger(), clock(), activity, getJobIdGenerator());
    }

    public JobIdGenerator getJobIdGenerator() {
        return JobIdGenerator.fromFileStorageAdaptor(logger(), getFileStorageAdaptor());
    }

    public AndroidNotificationFactory getAndroidNotificationFactory() {
        return new AndroidNotificationFactory(logger(), activity, PendingIntentRequestCodeGenerator.fromFileStorageAdaptor(logger(), getFileStorageAdaptor()));
    }

    public LearnificationAppDatabase getLearnificationAppDatabase() {
        return new LearnificationAppDatabase(activity);
    }
}
