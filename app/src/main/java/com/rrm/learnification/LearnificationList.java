package com.rrm.learnification;

import android.widget.ArrayAdapter;
import android.widget.ListView;

class LearnificationList {
    private static final String LOG_TAG = "LearnificationList";

    private AndroidLogger androidLogger;
    private MainActivityView mainActivityView;
    private ArrayAdapter<String> listViewAdapter;

    LearnificationList(AndroidLogger androidLogger, MainActivityView mainActivityView) {
        this.androidLogger = androidLogger;
        this.mainActivityView = mainActivityView;
    }

    void populate(LearnificationRepository learnificationRepository) {
        androidLogger.v(LOG_TAG, "populating learnification list");

        ListView listView = mainActivityView.getLearnificationListView();
        listView.setEnabled(true);
        listViewAdapter = mainActivityView.getListViewAdapter(learnificationRepository.learningItemsAsStringList());
        listView.setAdapter(listViewAdapter);
    }

    void addTextEntry(String textEntry) {
        androidLogger.v(LOG_TAG, "adding a text entry to the learnification list '" + textEntry + "'");
        listViewAdapter.add(textEntry);
    }
}
