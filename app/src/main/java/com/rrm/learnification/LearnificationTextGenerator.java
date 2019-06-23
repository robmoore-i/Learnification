package com.rrm.learnification;

import java.util.ArrayList;

class LearnificationTextGenerator {
    private Randomiser randomiser;

    LearnificationTextGenerator(Randomiser randomiser) {
        this.randomiser = randomiser;
    }

    String notificationText() {
        return randomiser.randomLearnificationQuestion(learningItems());
    }

    private ArrayList<LearningItem> learningItems() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(learningItemTemplate.build("Egypt", "Cairo"));
        learningItems.add(learningItemTemplate.build("Great Britain", "London"));
        learningItems.add(learningItemTemplate.build("Georgia", "Tbilisi"));
        return learningItems;
    }
}
