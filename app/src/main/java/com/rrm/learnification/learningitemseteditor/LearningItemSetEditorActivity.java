package com.rrm.learnification.learningitemseteditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.rrm.learnification.R;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.learnification.publication.AndroidLearnificationScheduler;
import com.rrm.learnification.learnification.publication.LearnificationScheduleStatusUpdate;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.LearningItemStash;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.UpdatableLearningItemTextDisplayStash;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.UpdateLearningItemButton;
import com.rrm.learnification.learningitemseteditor.toolbar.FastForwardScheduleButton;
import com.rrm.learnification.logdump.LogDumpActivity;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidActiveNotificationReader;
import com.rrm.learnification.notification.AndroidNotificationContext;
import com.rrm.learnification.notification.LearnificationNotificationChannelCreator;
import com.rrm.learnification.settings.SettingsActivity;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.LearningItemTextUpdateBroker;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.time.AndroidClock;

import static com.rrm.learnification.learningitemseteditor.SetButtonStatusOnTextChangeListener.textsValidationForDisplayedLearningItems;

public class LearningItemSetEditorActivity extends AppCompatActivity {
    private static final String LOG_TAG = "LearningItemSetEditorActivity";

    private final AndroidLogger logger = new AndroidLogger();
    private final AndroidClock clock = new AndroidClock();

    private LearningItemList learningItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learningitemseteditor);

        LearningItemSetEditorActivityBundle activityStartupParameters = LearningItemSetEditorActivityBundle.fromBundle(this.getIntent().getExtras());
        String learningItemSetName = activityStartupParameters.learningItemSetName;

        // Create some objects, in the order in which they have relevance in the view

        LearningItemSetEditorView learningItemSetEditorView = new LearningItemSetEditorView(logger, this);

        LearnificationAppDatabase learnificationAppDatabase = new LearnificationAppDatabase(this);
        SqlLearningItemSetRecordStore sqlLearningItemSetRecordStore = new SqlLearningItemSetRecordStore(logger, learnificationAppDatabase, learningItemSetName);
        LearningItemSqlTableClient learningItemSqlTableClient = new LearningItemSqlTableClient(logger, learnificationAppDatabase);

        LearningItemSetTitle learningItemSetTitle = new LearningItemSetTitle(logger, sqlLearningItemSetRecordStore, learningItemSetEditorView);
        LearningItemSetSelectorAdaptor learningItemSetSelectorAdaptor = new LearningItemSetSelectorAdaptor(logger, this, learningItemSqlTableClient.orderedLearningItemSetNames());
        LearningItemSetSelector learningItemSetSelector = new LearningItemSetSelector(logger, learningItemSetEditorView, learningItemSetSelectorAdaptor, learningItemSetTitle, sqlLearningItemSetRecordStore);

        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(learningItemSetEditorView);
        AddLearningItemButton addLearningItemButton = new AddLearningItemButton(logger, learningItemSetEditorView);

        PersistentLearningItemRepository learningItemRepository = new PersistentLearningItemRepository(logger, sqlLearningItemSetRecordStore, new LearningItemTextUpdateBroker());
        UpdatableLearningItemTextDisplayStash learningItemDisplayStash = new UpdatableLearningItemTextDisplayStash(logger, learningItemRepository);
        UpdateLearningItemButton updateLearningItemButton = new UpdateLearningItemButton(logger, learningItemSetEditorView, learningItemDisplayStash);

        SetButtonStatusOnTextChangeListener learningItemTextChangeListener = new SetButtonStatusOnTextChangeListener(logger, updateLearningItemButton);
        LearningItemStash learningItemStash = new LearningItemStash(logger, learningItemTextChangeListener, learningItemDisplayStash);
        learningItemList = new LearningItemList(logger, learningItemSetEditorView, new LearningItemListViewAdaptor(logger, learningItemStash, learningItemRepository), learningItemSetSelector);
        learningItemTextChangeListener.useTextValidation(textsValidationForDisplayedLearningItems(logger, learningItemList));

        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        JobIdGenerator jobIdGenerator = JobIdGenerator.fromFileStorageAdaptor(logger, fileStorageAdaptor);
        LearnificationScheduler learnificationScheduler = new AndroidLearnificationScheduler(logger, clock,
                new AndroidJobScheduler(logger, clock, this, jobIdGenerator),
                new ScheduleConfiguration(logger, new SettingsRepository(logger, fileStorageAdaptor)),
                new AndroidActiveNotificationReader(this.getSystemService(android.app.NotificationManager.class)));

        // Set them up where necessary, again in the order in which they have relevance in the view

        learningItemSetEditorView.addToolbarViewUpdate(new LearnificationScheduleStatusUpdate(logger, learnificationScheduler, new FastForwardScheduleButton(logger, learningItemSetEditorView)));

        learningItemSetSelector.select(learningItemSetName);
        learningItemSetTitle.set(learningItemSetName);

        learningItemTextInput.setOnTextChangeListener(new SetButtonStatusOnTextChangeListener(logger, addLearningItemButton));
        learningItemTextInput.setOnSubmitTextCommand(new SimulateButtonClickOnSubmitTextCommand(addLearningItemButton));
        addLearningItemButton.addOnClickHandler(new AddLearningItemOnClickCommand(logger, learningItemTextInput, learningItemRepository, learningItemList));
        addLearningItemButton.addOnClickHandler(new ClearTextInputOnClickCommand(learningItemTextInput));

        learningItemList.setOnSwipeCommand(new RemoveLearningItemOnSwipeCommand(learningItemRepository));

        updateLearningItemButton.addOnClickHandler((targetText, replacementText) -> {
            learningItemRepository.replace(targetText, replacementText);
            learningItemList.replace(targetText, replacementText);
        });

        // Schedule a learnification

        LearnificationNotificationChannelCreator channelCreator = new LearnificationNotificationChannelCreator(logger, new AndroidNotificationContext(this.getApplicationContext()));
        channelCreator.createNotificationChannel();
        learnificationScheduler.scheduleImminentLearnification();

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
        int id = item.getItemId();

        if (id == R.id.menu_action_settings) {
            logger.u(LOG_TAG, "selected settings menu item");
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.menu_action_dump_logs) {
            logger.u(LOG_TAG, "selected log dump menu item");
            startActivity(new Intent(this, LogDumpActivity.class));
            return true;
        }
        if (id == R.id.menu_action_refresh) {
            logger.u(LOG_TAG, "selected refresh menu item");
            learningItemList.refresh();
        }

        return super.onOptionsItemSelected(item);
    }
}
