package com.rrm.learnification;

import android.support.v7.widget.RecyclerView;

class LearnificationList {
    private static final String LOG_TAG = "LearnificationList";

    private AndroidLogger androidLogger;
    private MainActivityView mainActivityView;
    private LearnificationListViewAdaptor adapter;

    LearnificationList(AndroidLogger androidLogger, MainActivityView mainActivityView) {
        this.androidLogger = androidLogger;
        this.mainActivityView = mainActivityView;
    }

    void populate(LearnificationRepository learnificationRepository) {
        androidLogger.v(LOG_TAG, "populating learnification list");

        RecyclerView recyclerView = mainActivityView.getLearnificationList();
        recyclerView.setEnabled(true);
        adapter = new LearnificationListViewAdaptor(learnificationRepository.learningItemsAsStringList());
        recyclerView.setAdapter(adapter);
    }

    void addTextEntry(String textEntry) {
        androidLogger.v(LOG_TAG, "adding a text entry to the learnification list '" + textEntry + "'");
        adapter.add(textEntry);
    }
}
