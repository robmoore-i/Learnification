package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.rrm.learnification.R;
import com.rrm.learnification.common.AndroidClock;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.jobscheduler.AndroidJobScheduler;
import com.rrm.learnification.jobscheduler.JobIdGenerator;
import com.rrm.learnification.jobscheduler.JobScheduler;
import com.rrm.learnification.learnification.LearnificationScheduler;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.AndroidNotificationManager;
import com.rrm.learnification.notification.NotificationManager;
import com.rrm.learnification.schedulelog.FromFileScheduleLog;
import com.rrm.learnification.settings.ScheduleConfiguration;
import com.rrm.learnification.settings.SettingsActivity;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSqlTableInterface;
import com.rrm.learnification.storage.LearningItemStorage;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.storage.SqlLiteLearningItemStorage;

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
        SqlLiteLearningItemStorage learningItemStorage = new SqlLiteLearningItemStorage(new LearnificationAppDatabase(this), new LearningItemSqlTableInterface());
        PersistentLearningItemRepository learningItemRepository = new PersistentLearningItemRepository(logger, learningItemStorage);
        AndroidClock clock = new AndroidClock();
        AndroidJobScheduler jobScheduler = new AndroidJobScheduler(logger, this, JobIdGenerator.getInstance(), clock);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, new SettingsRepository(logger, fileStorageAdaptor));
        FromFileScheduleLog scheduleLog = new FromFileScheduleLog(logger, fileStorageAdaptor, clock);
        NotificationManager notificationManager = new AndroidNotificationManager(this.getSystemService(android.app.NotificationManager.class), NotificationManagerCompat.from(this), androidNotificationFacade);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, jobScheduler, scheduleConfiguration, scheduleLog, clock, notificationManager);

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

    public void clearSettings() {
        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        fileStorageAdaptor.deleteFile(SettingsRepository.LEARNIFICATION_DELAY_FILE_NAME);
        fileStorageAdaptor.deleteFile(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME);
    }

    public FileStorageAdaptor getFileStorageAdaptor() {
        return new AndroidInternalStorageAdaptor(logger, this);
    }

    public LearningItemStorage getLearningItemStorage() {
        return new SqlLiteLearningItemStorage(new LearnificationAppDatabase(this), new LearningItemSqlTableInterface());
    }

    public ItemRepository<LearningItem> getLearningItemRepository() {
        return new PersistentLearningItemRepository(logger, new SqlLiteLearningItemStorage(new LearnificationAppDatabase(this), new LearningItemSqlTableInterface()));
    }

    public JobScheduler getJobScheduler() {
        return new AndroidJobScheduler(logger, this, JobIdGenerator.getInstance(), new AndroidClock());
    }

    public AndroidNotificationFactory getAndroidNotificationFactory() {
        return new AndroidNotificationFactory(logger, this);
    }
}
