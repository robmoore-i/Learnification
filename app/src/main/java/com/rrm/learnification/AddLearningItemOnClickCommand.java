package com.rrm.learnification;

class AddLearningItemOnClickCommand implements OnClickCommand {
    private final LearningItemTextInput learningItemTextInput;
    private final LearningItemRepository learningItemRepository;
    private final LearningItemListView learningItemListView;

    AddLearningItemOnClickCommand(LearningItemTextInput learningItemTextInput, LearningItemRepository learningItemRepository, LearningItemListView learningItemListView) {
        this.learningItemTextInput = learningItemTextInput;
        this.learningItemRepository = learningItemRepository;
        this.learningItemListView = learningItemListView;
    }

    @Override
    public void onClick() {
        LearningItem learningItem = learningItemTextInput.getLearningItem();
        learningItemRepository.add(learningItem);
        learningItemListView.addTextEntry(learningItem.asSingleString());
    }
}
