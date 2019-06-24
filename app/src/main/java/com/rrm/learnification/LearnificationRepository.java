package com.rrm.learnification;

import java.util.ArrayList;
import java.util.List;

class LearnificationRepository {
    private List<LearningItem> learningItems;

    LearnificationRepository(List<LearningItem> learningItems) {
        this.learningItems = learningItems;
    }

    static LearnificationRepository getInstance() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(learningItemTemplate.build("Egypt", "Cairo"));
        learningItems.add(learningItemTemplate.build("Great Britain", "London"));
        learningItems.add(learningItemTemplate.build("Georgia", "Tbilisi"));
        return new LearnificationRepository(learningItems);
    }

    List<LearningItem> learningItems() {
        return learningItems;
    }

    List<String> learningItemsAsStringList() {
        List<LearningItem> learningItems = learningItems();

        ArrayList<String> stringifiedItems = new ArrayList<>();
        for (LearningItem learningItem : learningItems) {
            stringifiedItems.add(learningItem.asSingleString());
        }

        return stringifiedItems;
    }
}
