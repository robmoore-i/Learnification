package com.rrm.learnification.learnification;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemRepository;
import com.rrm.learnification.common.Randomiser;

import java.util.List;

public class LearnificationTextGenerator {
    private final LearningItemRepository learningItemRepository;
    private final Randomiser randomiser;

    public LearnificationTextGenerator(Randomiser randomiser, LearningItemRepository learningItemRepository) {
        this.randomiser = randomiser;
        this.learningItemRepository = learningItemRepository;
    }

    public LearnificationText learnificationText() throws CantGenerateNotificationTextException {
        List<LearningItem> learningItems = learningItemRepository.learningItems();
        if (learningItems.isEmpty()) {
            throw new CantGenerateNotificationTextException();
        }
        return randomiser.randomLearnificationQuestion(learningItems);
    }
}
