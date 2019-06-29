package com.rrm.learnification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

class LearnificationListView {
    private static final String LOG_TAG = "LearnificationListView";

    private AndroidLogger androidLogger;
    private MainActivityView mainActivityView;
    private LearnificationListViewAdaptor adapter;

    LearnificationListView(AndroidLogger androidLogger, MainActivityView mainActivityView) {
        this.androidLogger = androidLogger;
        this.mainActivityView = mainActivityView;
    }

    void populate(LearnificationRepository learnificationRepository) {
        androidLogger.v(LOG_TAG, "populating learnification list");

        RecyclerView recyclerView = mainActivityView.getLearnificationList();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                removeTextEntryAt(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView1, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter = new LearnificationListViewAdaptor(learnificationRepository.learningItemsAsStringList());
        recyclerView.setAdapter(adapter);
    }

    void addTextEntry(String textEntry) {
        androidLogger.v(LOG_TAG, "adding a text entry to the learnification list '" + textEntry + "'");
        adapter.add(textEntry);
    }

    private void removeTextEntryAt(int index) {
        androidLogger.v(LOG_TAG, "removing a text entry from the learnification list at index " + index);
        adapter.remove(index);
    }
}
