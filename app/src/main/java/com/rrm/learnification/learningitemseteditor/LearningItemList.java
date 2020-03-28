package com.rrm.learnification.learningitemseteditor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.textinput.TextEntryList;
import com.rrm.learnification.textlist.EditableTextListViewAdaptor;
import com.rrm.learnification.textlist.OnSwipeCommand;

import java.util.Collection;

class LearningItemList implements TextEntryList {
    private static final String LOG_TAG = "LearningItemList";

    private final RecyclerView recyclerView;
    private final AndroidLogger logger;

    private EditableTextListViewAdaptor adapter;

    LearningItemList(AndroidLogger logger, LearningItemListView view) {
        this.logger = logger;
        recyclerView = view.learningItemsList();
    }

    void bindTo(PersistentLearningItemRepository itemRepository) {
        logger.i(LOG_TAG, "populating learning-item list");
        LearningItemListViewAdaptor adapter = new LearningItemListViewAdaptor(logger, itemRepository.textEntries());
        recyclerView.setAdapter(adapter);
        this.adapter = adapter;
    }

    void setOnSwipeCommand(OnSwipeCommand onSwipeCommand) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int listViewIndex = viewHolder.getAdapterPosition();
                logger.i(LOG_TAG, "learning-item swiped at position " + listViewIndex + " to the " + swipeDirectionToString(swipeDir));
                onSwipeCommand.onSwipe(adapter, listViewIndex);
                logger.u(LOG_TAG, "removed learning item '" + adapter.itemAtPosition(listViewIndex) + "'");
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

    void addTextEntry(LearningItemText learningItemText) {
        logger.i(LOG_TAG, "adding a text entry to the learning-item list '" + learningItemText + "'");
        adapter.add(learningItemText.toString());
    }

    void useStash(LearningItemStash learningItemStash) {
        adapter.useStash(learningItemStash);
    }

    void replace(LearningItemText targetText, LearningItemText replacementText) {
        adapter.replace(targetText.toString(), replacementText.toString());
    }

    @Override
    public boolean containsTextEntries(Collection<String> textEntries) {
        return adapter.containsTextEntries(textEntries);
    }
}
