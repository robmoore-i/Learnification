package com.rrm.learnification.learningitemseteditor.learningitemlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.learningitemseteditor.learningitemremove.OnSwipeCommand;
import com.rrm.learnification.learningitemseteditor.learningitemsetselector.LearningItemSetChangeListener;
import com.rrm.learnification.learningitemseteditor.learningitemsetselector.LearningItemSetSelector;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.UpdatableTextEntryList;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.Collection;

public class LearningItemList implements UpdatableTextEntryList, LearningItemSetChangeListener {
    private static final String LOG_TAG = "LearningItemList";

    private final RecyclerView recyclerView;
    private final AndroidLogger logger;

    private LearningItemListViewAdaptor adapter;

    public LearningItemList(AndroidLogger logger, LearningItemListView view, LearningItemListViewAdaptor adapter,
                            LearningItemSetSelector learningItemSetSelector) {
        this.logger = logger;
        this.adapter = adapter;
        recyclerView = view.learningItemsList();
        recyclerView.setAdapter(adapter);
        learningItemSetSelector.registerForSetChanges(this);
    }

    public void setOnSwipeCommand(OnSwipeCommand onSwipeCommand) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int listViewIndex = viewHolder.getAdapterPosition();
                String deletedLearningItemText = adapter.itemAtPosition(listViewIndex);
                logger.i(LOG_TAG, "learning-item swiped at position " + listViewIndex + " to the " + swipeDirectionToString(swipeDir));
                onSwipeCommand.onSwipe(adapter, listViewIndex);
                logger.u(LOG_TAG, "removed learning item '" + deletedLearningItemText + "'");
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
            public boolean onMove(@NonNull RecyclerView recyclerView1, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void addTextEntry(LearningItemText learningItemText) {
        logger.i(LOG_TAG, "adding a text entry to the learning-item list '" + learningItemText + "'");
        adapter.add(learningItemText.toString());
    }

    public void replace(LearningItemText targetText, LearningItemText replacementText) {
        adapter.replace(targetText.toString(), replacementText.toString());
    }

    @Override
    public boolean containsTextEntries(Collection<String> textEntries) {
        return adapter.containsTextEntries(textEntries);
    }

    @Override
    public void refresh() {
        adapter.refresh();
    }
}
