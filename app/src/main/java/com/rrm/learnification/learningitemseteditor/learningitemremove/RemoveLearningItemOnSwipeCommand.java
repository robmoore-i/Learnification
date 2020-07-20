package com.rrm.learnification.learningitemseteditor.learningitemremove;

import com.rrm.learnification.learningitemseteditor.learningitemlist.LearningItemListViewAdaptor;
import com.rrm.learnification.learningitemstorage.PersistentLearningItemRepository;

public class RemoveLearningItemOnSwipeCommand implements OnSwipeCommand {
    private final PersistentLearningItemRepository itemRepository;

    public RemoveLearningItemOnSwipeCommand(PersistentLearningItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void onSwipe(LearningItemListViewAdaptor adapter, int index) {
        adapter.remove(index);
        itemRepository.removeAt(index);
    }
}
