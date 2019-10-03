package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.rrm.learnification.R;
import com.rrm.learnification.common.AndroidClock;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.jobscheduler.AndroidJobScheduler;
import com.rrm.learnification.jobscheduler.JobIdGenerator;
import com.rrm.learnification.jobscheduler.JobScheduler;
import com.rrm.learnification.learnification.LearnificationScheduler;
import com.rrm.learnification.learnification.ScheduleConfiguration;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.schedulelog.FromFileScheduleLog;
import com.rrm.learnification.settings.SettingsActivity;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.FromFileLearningItemStorage;
import com.rrm.learnification.storage.LearningItemStorage;
import com.rrm.learnification.storage.PersistentLearningItemRepository;

public class MainActivity extends AppCompatActivity {
    private final AndroidLogger logger = new AndroidLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dependency construction
        MainActivityView mainActivityView = new MainActivityView(logger, this);
        AndroidNotificationFacade androidNotificationFacade = AndroidNotificationFacade.fromContext(logger, this);
        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        PersistentLearningItemRepository learningItemRepository = new PersistentLearningItemRepository(logger, new FromFileLearningItemStorage(logger, fileStorageAdaptor));
        AndroidClock clock = new AndroidClock();
        AndroidJobScheduler jobScheduler = new AndroidJobScheduler(this, JobIdGenerator.getInstance());
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, new SettingsRepository(logger, fileStorageAdaptor));
        FromFileScheduleLog scheduleLog = new FromFileScheduleLog(logger, fileStorageAdaptor, clock);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, jobScheduler, scheduleConfiguration, scheduleLog, clock);

        // Entry point
        MainActivityEntryPoint mainActivityEntryPoint = new MainActivityEntryPoint(
                logger,
                mainActivityView,
                androidNotificationFacade,
                learningItemRepository,
                learnificationScheduler
        );
        mainActivityEntryPoint.onMainActivityEntry();

        // Don't automatically open the keyboard.
        //      See: https://stackoverflow.com/questions/9732761/prevent-the-keyboard-from-displaying-on-activity-start
        // Also don't automatically shift the view up when the keyboard opens
        //      See: https://stackoverflow.com/questions/4207880/android-how-do-i-prevent-the-soft-keyboard-from-pushing-my-view-up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearData() {
        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        fileStorageAdaptor.deleteFile(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME);
        fileStorageAdaptor.deleteFile(SettingsRepository.PERIODICITY_FILE_NAME);
        fileStorageAdaptor.deleteFile(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME);
    }

    public FileStorageAdaptor getFileStorageAdaptor() {
        return new AndroidInternalStorageAdaptor(logger, this);
    }

    public LearningItemStorage getLearningItemStorage() {
        return new FromFileLearningItemStorage(logger, new AndroidInternalStorageAdaptor(logger, this));
    }

    public JobScheduler getJobScheduler() {
        return new AndroidJobScheduler(this, JobIdGenerator.getInstance());
    }
}
