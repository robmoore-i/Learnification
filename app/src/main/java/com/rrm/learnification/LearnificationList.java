package com.rrm.learnification;

import android.widget.ArrayAdapter;
import android.widget.ListView;

class LearnificationList {
    private MainActivity activity;
    private MainActivityView mainActivityView;
    private ArrayAdapter<String> listViewAdapter;

    LearnificationList(MainActivity activity, MainActivityView mainActivityView) {
        this.activity = activity;
        this.mainActivityView = mainActivityView;
    }

    void populate(LearnificationRepository learnificationRepository) {
        ListView listView = mainActivityView.getLearnificationListView();
        listView.setEnabled(true);
        listViewAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, learnificationRepository.learningItemsAsStringList());
        listView.setAdapter(listViewAdapter);
    }

    void addTextEntry(String string) {
        listViewAdapter.add(string);
    }
}
