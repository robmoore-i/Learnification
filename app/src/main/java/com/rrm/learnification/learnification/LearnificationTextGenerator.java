package com.rrm.learnification.learnification;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.notification.CantGenerateNotificationTextException;
import com.rrm.learnification.random.Randomiser;
import com.rrm.learnification.storage.ItemRepository;

import java.util.List;

public class LearnificationTextGenerator {
    private final ItemRepository<LearningItem> itemRepository;
    private final Randomiser randomiser;

    public LearnificationTextGenerator(Randomiser randomiser, ItemRepository<LearningItem> itemRepository) {
        this.randomiser = randomiser;
        this.itemRepository = itemRepository;
    }

    public LearnificationText learnificationText() throws CantGenerateNotificationTextException {
        List<LearningItem> learningItems = itemRepository.items();
        if (learningItems.isEmpty()) {
            throw new CantGenerateNotificationTextException();
        }
        return randomiser.randomLearnificationQuestion(learningItems);
    }
}
