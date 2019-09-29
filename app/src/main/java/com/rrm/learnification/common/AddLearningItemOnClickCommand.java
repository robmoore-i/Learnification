package com.rrm.learnification.common;

class AddLearningItemOnClickCommand implements OnClickCommand {
    private final LearningItemTextInput learningItemTextInput;
    private final LearningItemRepository learningItemRepository;
    private final LearningItemList learningItemList;

    AddLearningItemOnClickCommand(LearningItemTextInput learningItemTextInput, LearningItemRepository learningItemRepository, LearningItemList learningItemList) {
        this.learningItemTextInput = learningItemTextInput;
        this.learningItemRepository = learningItemRepository;
        this.learningItemList = learningItemList;
    }

    @Override
    public void onClick() {
        LearningItem learningItem = learningItemTextInput.getLearningItem();
        learningItemRepository.add(learningItem);
        learningItemList.addTextEntry(learningItem.asSingleString());
    }
}
