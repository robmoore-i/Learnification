package com.rrm.learnification;

import java.util.List;

class LearnificationTextGenerator {
    private final LearningItemRepository learningItemRepository;
    private final Randomiser randomiser;

    LearnificationTextGenerator(Randomiser randomiser, LearningItemRepository learningItemRepository) {
        this.randomiser = randomiser;
        this.learningItemRepository = learningItemRepository;
    }

    LearnificationText learnificationText() throws CantGenerateNotificationTextException {
        List<LearningItem> learningItems = learningItemRepository.learningItems();
        if (learningItems.isEmpty()) {
            throw new CantGenerateNotificationTextException();
        }
        return randomiser.randomLearnificationQuestion(learningItems);
    }
}
