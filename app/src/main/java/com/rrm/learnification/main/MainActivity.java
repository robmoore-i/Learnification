package com.rrm.learnification.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.rrm.learnification.R;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.learnification.FromFileScheduleLog;
import com.rrm.learnification.notification.AndroidNotificationFacade;
import com.rrm.learnification.random.JavaRandomiser;
import com.rrm.learnification.random.Randomiser;
import com.rrm.learnification.settings.SettingsActivity;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.FromFileLearningItemStorage;
import com.rrm.learnification.storage.PersistentLearningItemRepository;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dependency construction
        AndroidLogger logger = new AndroidLogger();
        MainActivityView mainActivityView = new MainActivityView(logger, this);
        AndroidNotificationFacade androidNotificationFacade = AndroidNotificationFacade.fromContext(logger, this);
        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        Randomiser randomiser = new JavaRandomiser();

        // Entry point
        MainActivityEntryPoint mainActivityEntryPoint = new MainActivityEntryPoint(
                logger,
                mainActivityView,
                androidNotificationFacade,
                randomiser,
                new PersistentLearningItemRepository(logger, new FromFileLearningItemStorage(logger, fileStorageAdaptor))
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
        AndroidLogger logger = new AndroidLogger();
        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        fileStorageAdaptor.deleteFile(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME);
        fileStorageAdaptor.deleteFile(SettingsRepository.PERIODICITY_FILE_NAME);
        fileStorageAdaptor.deleteFile(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME);
    }

    public AndroidInternalStorageAdaptor getAndroidInternalStorageAdaptor() {
        return new AndroidInternalStorageAdaptor(new AndroidLogger(), this);
    }

    public FromFileLearningItemStorage getFromFileLearnificationStorage() {
        AndroidLogger logger = new AndroidLogger();
        return new FromFileLearningItemStorage(logger, new AndroidInternalStorageAdaptor(logger, this));
    }
}
