package com.rrm.learnification;

import java.util.ArrayList;

class LearnificationTextGenerator {
    String notificationText() {
        return chooseRandomLearningItemSide(chooseRandomLearningItem(learningItems()));
    }

    private String chooseRandomLearningItemSide(LearningItem learningItem) {
        return learningItem.left;
    }

    private LearningItem chooseRandomLearningItem(ArrayList<LearningItem> learningItems) {
        return learningItems.get(0);
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
