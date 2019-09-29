package com.rrm.learnification.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;
import java.util.stream.Collectors;

class LearningItemList {
    private static final String LOG_TAG = "LearningItemList";

    private final RecyclerView recyclerView;
    private final AndroidLogger logger;

    private LearningItemListViewAdaptor adapter;

    LearningItemList(AndroidLogger logger, LearningItemListView learningItemListView) {
        this.logger = logger;
        recyclerView = learningItemListView.learningItemsList();
    }

    void bindTo(LearningItemRepository learningItemRepository) {
        logger.v(LOG_TAG, "populating learning-item list");
        List<String> learningItemsAsTextEntries = learningItemRepository.learningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
        LearningItemListViewAdaptor adapter = new LearningItemListViewAdaptor(logger, learningItemsAsTextEntries);
        recyclerView.setAdapter(adapter);
        this.adapter = adapter;
    }

    void setOnSwipeCommand(OnSwipeCommand onSwipeCommand) {
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

    void addTextEntry(String textEntry) {
        logger.v(LOG_TAG, "adding a text entry to the learning-item list '" + textEntry + "'");
        adapter.add(textEntry);
    }
}
