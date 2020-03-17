package com.rrm.learnification.textlist;

import com.rrm.learnification.storage.PersistentLearningItemRepository;

public class RemoveLearningItemOnSwipeCommand implements OnSwipeCommand {
    private final PersistentLearningItemRepository itemRepository;

    public RemoveLearningItemOnSwipeCommand(PersistentLearningItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void onSwipe(EditableTextListViewAdaptor adapter, int index) {
        adapter.remove(index);
        itemRepository.removeAt(index);
    }
}
