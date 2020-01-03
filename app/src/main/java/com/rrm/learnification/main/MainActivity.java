package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.rrm.learnification.R;
import com.rrm.learnification.button.ClearTextInputOnClickCommand;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.AndroidResponseNotificationCorrespondent;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.settings.SettingsActivity;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSqlTableInterface;
import com.rrm.learnification.storage.LearningItemStorage;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.storage.SqlLiteLearningItemStorage;
import com.rrm.learnification.textinput.SetButtonStatusOnTextChangeListener;
import com.rrm.learnification.textinput.SimulateButtonClickOnSubmitTextCommand;
import com.rrm.learnification.textlist.RemoveItemOnSwipeCommand;
import com.rrm.learnification.time.AndroidClock;
import com.rrm.learnification.toolbar.FastForwardScheduleButton;
import com.rrm.learnification.toolbar.LearnificationScheduleStatusUpdate;

public class MainActivity extends AppCompatActivity {
    private final AndroidLogger logger = new AndroidLogger();
    private final AndroidClock clock = new AndroidClock();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create some objects

        MainActivityView mainActivityView = new MainActivityView(logger, this);
        AndroidNotificationFacade androidNotificationFacade = AndroidNotificationFacade.fromContext(logger, this);
        PersistentLearningItemRepository learningItemRepository = new PersistentLearningItemRepository(logger, new SqlLiteLearningItemStorage(logger, new LearnificationAppDatabase(this), new LearningItemSqlTableInterface()));
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, new AndroidJobScheduler(logger, this, JobIdGenerator.getInstance(), clock), new ScheduleConfiguration(logger, new SettingsRepository(logger, new AndroidInternalStorageAdaptor(logger, this))), clock, new AndroidResponseNotificationCorrespondent(this.getSystemService(android.app.NotificationManager.class), NotificationManagerCompat.from(this), androidNotificationFacade));
        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(mainActivityView);
        AddLearningItemButton addLearningItemButton = new AddLearningItemButton(logger, mainActivityView);
        LearningItemList learningItemList = new LearningItemList(logger, mainActivityView);
        UpdateLearningItemButton updateLearningItemButton = new UpdateLearningItemButton(logger, mainActivityView);

        // Set them up where necessary

        mainActivityView.addToolbarViewUpdate(new LearnificationScheduleStatusUpdate(logger, learnificationScheduler, new FastForwardScheduleButton(logger, mainActivityView)));

        learningItemTextInput.setOnTextChangeListener(new SetButtonStatusOnTextChangeListener(addLearningItemButton));
        learningItemTextInput.setOnSubmitTextCommand(new SimulateButtonClickOnSubmitTextCommand(addLearningItemButton));
        addLearningItemButton.addOnClickHandler(new AddLearningItemOnClickCommand(learningItemTextInput, learningItemRepository, learningItemList));
        addLearningItemButton.addOnClickHandler(new ClearTextInputOnClickCommand(learningItemTextInput));

        learningItemList.bindTo(learningItemRepository);
        learningItemList.setOnSwipeCommand(new RemoveItemOnSwipeCommand(learningItemRepository));

        androidNotificationFacade.createNotificationChannel(AndroidNotificationFacade.CHANNEL_ID);
        learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);

        // Do other stuff

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
        fileStorageAdaptor.deleteFile(SettingsRepository.LEARNIFICATION_PROMPT_STRATEGY_FILE_NAME);
    }

    public FileStorageAdaptor getFileStorageAdaptor() {
        return new AndroidInternalStorageAdaptor(logger, this);
    }

    public LearningItemStorage getLearningItemStorage() {
        return new SqlLiteLearningItemStorage(logger, new LearnificationAppDatabase(this), new LearningItemSqlTableInterface());
    }

    public ItemRepository<LearningItem> getLearningItemRepository() {
        return new PersistentLearningItemRepository(logger, new SqlLiteLearningItemStorage(logger, new LearnificationAppDatabase(this), new LearningItemSqlTableInterface()));
    }

    public JobScheduler getJobScheduler() {
        return new AndroidJobScheduler(logger, this, JobIdGenerator.getInstance(), new AndroidClock());
    }

    public AndroidNotificationFactory getAndroidNotificationFactory() {
        return new AndroidNotificationFactory(logger, this);
    }
}
