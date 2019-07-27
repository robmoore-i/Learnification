package com.rrm.learnification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;
import java.util.stream.Collectors;

class LearnificationListView {
    private static final String LOG_TAG = "LearnificationListView";

    private final RecyclerView recyclerView;
    private final AndroidLogger logger;

    private LearnificationListViewAdaptor adapter;
    private OnSwipeCommand onSwipeCommand;

    LearnificationListView(AndroidLogger logger, MainActivityView mainActivityView) {
        this.logger = logger;
        recyclerView = mainActivityView.learningItemsList();
    }

    void bindTo(LearnificationRepository learnificationRepository) {
        logger.v(LOG_TAG, "populating learnification list");
        List<String> learningItemsAsTextEntries = learnificationRepository.learningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
        LearnificationListViewAdaptor adapter = new LearnificationListViewAdaptor(learningItemsAsTextEntries);
        recyclerView.setAdapter(adapter);
        this.adapter = adapter;
    }

    void setOnSwipeCommand(OnSwipeCommand onSwipeCommand) {
        this.onSwipeCommand = onSwipeCommand;
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int listViewIndex = viewHolder.getAdapterPosition();
                logger.v(LOG_TAG, "learning-item swiped at position " + listViewIndex + " to the " + swipeDirectionToString(swipeDir));
                onSwipeCommand.onSwipe(adapter, listViewIndex);
            }

            private String swipeDirectionToString(int swipeDir) {
                if (swipeDir == ItemTouchHelper.LEFT) {
                    return "left";
                } else if (swipeDir == ItemTouchHelper.RIGHT) {
                    return "right";
                } else {
                    return "neither left nor right";
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView1, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    void swipeOnItem(int index) {
        onSwipeCommand.onSwipe(adapter, index);
    }

    void addTextEntry(String textEntry) {
        logger.v(LOG_TAG, "adding a text entry to the learnification list '" + textEntry + "'");
        adapter.add(textEntry);
    }
}
