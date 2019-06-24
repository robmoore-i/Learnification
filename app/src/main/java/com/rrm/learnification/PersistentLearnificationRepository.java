package com.rrm.learnification;

import java.util.ArrayList;
import java.util.List;

class PersistentLearnificationRepository implements LearnificationRepository {
    private List<LearningItem> learningItems;

    PersistentLearnificationRepository(List<LearningItem> learningItems) {
        this.learningItems = learningItems;
    }

    static LearnificationRepository createInstance() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(learningItemTemplate.build("Egypt", "Cairo"));
        learningItems.add(learningItemTemplate.build("Great Britain", "London"));
        learningItems.add(learningItemTemplate.build("Georgia", "Tbilisi"));
        return new PersistentLearnificationRepository(learningItems);
    }

    @Override
    public List<LearningItem> learningItems() {
        return learningItems;
    }

    @Override
    public List<String> learningItemsAsStringList() {
        List<LearningItem> learningItems = learningItems();

        ArrayList<String> stringifiedItems = new ArrayList<>();
        for (LearningItem learningItem : learningItems) {
            stringifiedItems.add(learningItem.asSingleString());
        }

        return stringifiedItems;
    }
}
