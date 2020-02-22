package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.PendingIntentRequestCodeGenerator;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.storage.ItemStorage;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemChangeListenerGroup;
import com.rrm.learnification.storage.LearningItemSqlTableInterface;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.storage.SqlLiteLearningItemStorage;
import com.rrm.learnification.time.AndroidClock;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LearningItemSetEditorActivity.class));
    }

    public AndroidLogger logger() {
        return new AndroidLogger();
    }

    public AndroidClock clock() {
        return new AndroidClock();
    }

    public void clearSettings() {
        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger(), this);
        fileStorageAdaptor.deleteFile(SettingsRepository.LEARNIFICATION_DELAY_FILE_NAME);
        fileStorageAdaptor.deleteFile(SettingsRepository.LEARNIFICATION_PROMPT_STRATEGY_FILE_NAME);
    }

    public FileStorageAdaptor getFileStorageAdaptor() {
        return new AndroidInternalStorageAdaptor(logger(), this);
    }

    public ItemStorage<LearningItem> getLearningItemStorage() {
        return new SqlLiteLearningItemStorage(logger(), new LearnificationAppDatabase(this), new LearningItemSqlTableInterface());
    }

    public ItemRepository<LearningItem> getLearningItemRepository() {
        return new PersistentLearningItemRepository(logger(), getLearningItemStorage(), new LearningItemChangeListenerGroup());
    }

    public JobScheduler getJobScheduler() {
        return new AndroidJobScheduler(logger(), clock(), this, getJobIdGenerator());
    }

    public JobIdGenerator getJobIdGenerator() {
        return JobIdGenerator.fromFileStorageAdaptor(logger(), getFileStorageAdaptor());
    }

    public AndroidNotificationFactory getAndroidNotificationFactory() {
        return new AndroidNotificationFactory(logger(), this, getPendingIntentRequestCodeGenerator());
    }

    public PendingIntentRequestCodeGenerator getPendingIntentRequestCodeGenerator() {
        return PendingIntentRequestCodeGenerator.fromFileStorageAdaptor(logger(), getFileStorageAdaptor());
    }
}
