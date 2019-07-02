package com.rrm.learnification;

import java.util.List;

class LearnificationTextGenerator {
    private final LearnificationRepository learnificationRepository;
    private Randomiser randomiser;

    LearnificationTextGenerator(Randomiser randomiser, LearnificationRepository learnificationRepository) {
        this.randomiser = randomiser;
        this.learnificationRepository = learnificationRepository;
    }

    String notificationText() throws CantGenerateNotificationTextException {
        List<LearningItem> learningItems = learnificationRepository.learningItems();
        if (learningItems.isEmpty()) {
            throw new CantGenerateNotificationTextException();
        }
        return randomiser.randomLearnificationQuestion(learningItems);
    }
}
