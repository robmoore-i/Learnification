package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.learningitemstorage.PersistentLearningItemRepository;
import com.rrm.learnification.logger.AndroidLogger;

class AddLearningItemOnClickCommand implements OnClickCommand {
    private static final String LOG_TAG = "AddLearningItemOnClickCommand";
    private final AndroidLogger logger;

    private final LearningItemTextInput learningItemTextInput;
    private final PersistentLearningItemRepository itemRepository;
    private final LearningItemList learningItemList;

    AddLearningItemOnClickCommand(AndroidLogger logger, LearningItemTextInput learningItemTextInput, LearningItemList learningItemList,
                                  PersistentLearningItemRepository itemRepository) {
        this.logger = logger;
        this.learningItemTextInput = learningItemTextInput;
        this.itemRepository = itemRepository;
        this.learningItemList = learningItemList;
    }

    @Override
    public void onClick() {
        try {
            LearningItemText displayText = learningItemTextInput.getText();
            logger.u(LOG_TAG, "added learning item '" + displayText + "'");
            itemRepository.add(displayText);
            learningItemList.addTextEntry(displayText);
        } catch (Exception e) {
            throw new CantAddLearningItemException(e);
        }
    }
}
