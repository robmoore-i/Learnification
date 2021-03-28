package com.rrm.learnification.navbar;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.SettingsActivity;

public class Navbar {
    private static final String LOG_TAG = "Navbar";

    private final AndroidLogger logger;
    private final BottomNavigationView bottomNavigationView;
    private final Activity activity;

    public Navbar(AndroidLogger logger, LearningItemSetEditorActivity activity) {
        this(logger, activity, R.id.navigation_learningitemseteditor);
    }

    public Navbar(AndroidLogger logger, SettingsActivity activity) {
        this(logger, activity, R.id.navigation_settings);
    }

    private Navbar(AndroidLogger logger, Activity activity, int selectedTabItemId) {
        this.logger = logger;
        this.activity = activity;
        this.bottomNavigationView = activity.findViewById(R.id.navbar);
        this.bottomNavigationView.setSelectedItemId(selectedTabItemId);
    }


    public void bindTabChoicesToActivities() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_settings) {
                logger.u(LOG_TAG, "selected settings navbar item");
                activity.startActivity(new Intent(activity, SettingsActivity.class));
                return true;
            }
            if (id == R.id.navigation_learningitemseteditor) {
                logger.u(LOG_TAG, "selected learning item set editor navbar item");
                activity.startActivity(new Intent(activity, LearningItemSetEditorActivity.class));
                return true;
            }
            return true;
        });
    }
}
