package com.rrm.learnification;

import java.util.List;
import java.util.Random;

class JavaRandomiser implements Randomiser {
    private Random random = new Random();

    @Override
    public String randomLearnificationQuestion(List<LearningItem> learningItems) {
        return chooseRandomLearningItemSide(chooseRandomLearningItem(learningItems));
    }

    private LearningItem chooseRandomLearningItem(List<LearningItem> learningItems) {
        int n = random.nextInt(learningItems.size());
        return learningItems.get(n);
    }

    private String chooseRandomLearningItemSide(LearningItem learningItem) {
        if (random.nextBoolean()) {
            return learningItem.left;
        } else {
            return learningItem.right;
        }
    }
}
