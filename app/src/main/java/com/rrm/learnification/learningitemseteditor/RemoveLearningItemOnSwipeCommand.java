package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.textlist.OnSwipeCommand;

public class RemoveLearningItemOnSwipeCommand implements OnSwipeCommand {
    private final PersistentLearningItemRepository itemRepository;

    RemoveLearningItemOnSwipeCommand(PersistentLearningItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void onSwipe(LearningItemListViewAdaptor adapter, int index) {
        adapter.remove(index);
        itemRepository.removeAt(index);
    }
}