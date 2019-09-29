package com.rrm.learnification.common;

class RemoveItemOnSwipeCommand implements OnSwipeCommand {
    private final LearningItemRepository learningItemRepository;

    RemoveItemOnSwipeCommand(LearningItemRepository learningItemRepository) {
        this.learningItemRepository = learningItemRepository;
    }

    @Override
    public void onSwipe(LearningItemListViewAdaptor adapter, int index) {
        adapter.remove(index);
        learningItemRepository.removeAt(index);
    }
}
