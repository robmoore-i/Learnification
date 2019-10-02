package com.rrm.learnification.main;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.OnClickCommand;
import com.rrm.learnification.storage.ItemRepository;

class AddLearningItemOnClickCommand implements OnClickCommand {
    private final LearningItemTextInput learningItemTextInput;
    private final ItemRepository<LearningItem> itemRepository;
    private final LearningItemList learningItemList;

    AddLearningItemOnClickCommand(LearningItemTextInput learningItemTextInput, ItemRepository<LearningItem> itemRepository, LearningItemList learningItemList) {
        this.learningItemTextInput = learningItemTextInput;
        this.itemRepository = itemRepository;
        this.learningItemList = learningItemList;
    }

    @Override
    public void onClick() {
        LearningItem learningItem = learningItemTextInput.getLearningItem();
        itemRepository.add(learningItem);
        learningItemList.addTextEntry(learningItem.asSingleString());
    }
}
