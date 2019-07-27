package com.rrm.learnification;

class AddLearningItemOnClickCommand implements OnClickCommand {
    private final LearningItemRepository learningItemRepository;
    private final LearningItemListView learningItemListView;
    private MainActivityView mainActivityView;

    AddLearningItemOnClickCommand(MainActivityView mainActivityView, LearningItemRepository learningItemRepository, LearningItemListView learningItemListView) {
        this.mainActivityView = mainActivityView;
        this.learningItemRepository = learningItemRepository;
        this.learningItemListView = learningItemListView;
    }

    @Override
    public void onClick() {
        LearningItem learningItem = mainActivityView.getLearningItemTextInput();
        learningItemRepository.add(learningItem);
        learningItemListView.addTextEntry(learningItem.asSingleString());
    }
}
