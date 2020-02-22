package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.common.LearningItem;
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
        try {
            LearningItem learningItem = learningItemTextInput.getLearningItem();
            itemRepository.add(learningItem);
            learningItemList.addTextEntry(learningItem.asSingleString());
        } catch (Exception e) {
            throw new CantAddLearningItemException(e);
        }
    }
}
