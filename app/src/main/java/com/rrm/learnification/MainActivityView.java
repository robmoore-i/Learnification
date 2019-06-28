package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;

class MainActivityView {
    private MainActivity activity;

    MainActivityView(MainActivity activity) {
        this.activity = activity;
    }

    FloatingActionButton getLearnificationButton() {
        return activity.findViewById(R.id.addLearnificationButton);
    }

    ListView getLearnificationListView() {
        return activity.findViewById(R.id.learnificationsListView);
    }
}
