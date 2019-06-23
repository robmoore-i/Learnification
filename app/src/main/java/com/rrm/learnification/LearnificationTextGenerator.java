package com.rrm.learnification;

import java.util.ArrayList;

class LearnificationTextGenerator {
    String notificationText() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        LearningItem cities1 = learningItemTemplate.build("Egypt", "Cairo");
        LearningItem cities2 = learningItemTemplate.build("Great Britain", "London");
        LearningItem cities3 = learningItemTemplate.build("Georgia", "Tbilisi");

        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(cities1);
        learningItems.add(cities2);
        learningItems.add(cities3);

        return learningItems.get(0).left;
    }
}
