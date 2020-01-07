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
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.notification.PendingIntentRequestCodeGenerator;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.settings.SettingsActivity;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.storage.ItemStorage;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemChangeListenerGroup;
import com.rrm.learnification.storage.LearningItemSqlTableInterface;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.storage.SqlLiteLearningItemStorage;
import com.rrm.learnification.textinput.SetButtonStatusOnTextChangeListener;
import com.rrm.learnification.textinput.SimulateButtonClickOnSubmitTextCommand;
import com.rrm.learnification.textlist.RemoveItemOnSwipeCommand;
import com.rrm.learnification.time.AndroidClock;
import com.rrm.learnification.toolbar.FastForwardScheduleButton;
import com.rrm.learnification.toolbar.LearnificationScheduleStatusUpdate;

import static com.rrm.learnification.textinput.SetButtonStatusOnTextChangeListener.noneEmpty;
import static com.rrm.learnification.textinput.SetButtonStatusOnTextChangeListener.unpersistedValidLearningItemSingleTextEntries;

public class MainActivity extends AppCompatActivity {
    private final AndroidLogger logger = new AndroidLogger();
    private final AndroidClock clock = new AndroidClock();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create some objects, in the order in which they have relevance in the view

        MainActivityView mainActivityView = new MainActivityView(logger, this);

        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(mainActivityView);
        AddLearningItemButton addLearningItemButton = new AddLearningItemButton(logger, mainActivityView);

        LearningItemList learningItemList = new LearningItemList(logger, mainActivityView);

        PersistentLearningItemRepository learningItemRepository = new PersistentLearningItemRepository(logger, new SqlLiteLearningItemStorage(logger, new LearnificationAppDatabase(this), new LearningItemSqlTableInterface()), new LearningItemChangeListenerGroup());
        UpdatedLearningItemSaver updatedLearningItemSaver = new UpdatedLearningItemSaver(logger, learningItemRepository);
        UpdateLearningItemButton updateLearningItemButton = new UpdateLearningItemButton(logger, mainActivityView, updatedLearningItemSaver);

        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        NotificationIdGenerator notificationIdGenerator = NotificationIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor);
        PendingIntentRequestCodeGenerator pendingIntentRequestCodeGenerator = PendingIntentRequestCodeGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor);
        AndroidNotificationFacade androidNotificationFacade = AndroidNotificationFacade.fromContext(logger, this, notificationIdGenerator, pendingIntentRequestCodeGenerator);
        JobIdGenerator jobIdGenerator = JobIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, clock,
                new AndroidJobScheduler(logger, clock, this, jobIdGenerator),
                new ScheduleConfiguration(logger, new SettingsRepository(logger, fileStorageAdaptor)),
                new AndroidResponseNotificationCorrespondent(
                        logger,
                        this.getSystemService(android.app.NotificationManager.class),
                        NotificationManagerCompat.from(this),
                        androidNotificationFacade.getFactory(),
                        notificationIdGenerator));

        // Set them up where necessary, again in the order in which they have relevance in the view

        mainActivityView.addToolbarViewUpdate(new LearnificationScheduleStatusUpdate(logger, learnificationScheduler, new FastForwardScheduleButton(logger, mainActivityView)));

        learningItemTextInput.setOnTextChangeListener(new SetButtonStatusOnTextChangeListener(logger, addLearningItemButton, noneEmpty));
        learningItemTextInput.setOnSubmitTextCommand(new SimulateButtonClickOnSubmitTextCommand(addLearningItemButton));
        addLearningItemButton.addOnClickHandler(new AddLearningItemOnClickCommand(learningItemTextInput, learningItemRepository, learningItemList));
        addLearningItemButton.addOnClickHandler(new ClearTextInputOnClickCommand(learningItemTextInput));

        learningItemList.bindTo(learningItemRepository);
        learningItemList.setOnSwipeCommand(new RemoveItemOnSwipeCommand(learningItemRepository));
        learningItemList.setEntryUpdateHandlers(
                new SetButtonStatusOnTextChangeListener(logger, updateLearningItemButton, unpersistedValidLearningItemSingleTextEntries(logger, learningItemRepository)),
                updatedLearningItemSaver
        );

        updateLearningItemButton.addOnClickHandler(learningItemRepository::replace);

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

    public ItemStorage<LearningItem> getLearningItemStorage() {
        return new SqlLiteLearningItemStorage(logger, new LearnificationAppDatabase(this), new LearningItemSqlTableInterface());
    }

    public ItemRepository<LearningItem> getLearningItemRepository() {
        return new PersistentLearningItemRepository(logger, getLearningItemStorage(), new LearningItemChangeListenerGroup());
    }

    public JobScheduler getJobScheduler() {
        return new AndroidJobScheduler(logger, clock, this, getJobIdGenerator());
    }

    public JobIdGenerator getJobIdGenerator() {
        return JobIdGenerator.fromFileStorageAdaptor(logger, getFileStorageAdaptor());
    }

    public AndroidNotificationFactory getAndroidNotificationFactory() {
        return new AndroidNotificationFactory(logger, this, getPendingIntentRequestCodeGenerator());
    }

    public PendingIntentRequestCodeGenerator getPendingIntentRequestCodeGenerator() {
        return PendingIntentRequestCodeGenerator.fromFileStorageAdaptor(logger, getFileStorageAdaptor());
    }

    public NotificationIdGenerator getNotificationIdGenerator() {
        return NotificationIdGenerator.fromFileStorageAdaptor(logger, getFileStorageAdaptor());
    }
}
