package com.rrm.learnification;

class LearnificationTextGenerator {
    private final LearnificationRepository learnificationRepository;
    private Randomiser randomiser;

    LearnificationTextGenerator(Randomiser randomiser, LearnificationRepository learnificationRepository) {
        this.randomiser = randomiser;
        this.learnificationRepository = learnificationRepository;
    }

    String notificationText() {
        return randomiser.randomLearnificationQuestion(learnificationRepository.learningItems());
    }
}
