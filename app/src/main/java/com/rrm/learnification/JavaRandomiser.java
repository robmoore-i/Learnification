package com.rrm.learnification;

import java.util.ArrayList;
import java.util.Random;

class JavaRandomiser implements Randomiser {
    private Random random = new Random();

    @Override
    public String randomLearnificationQuestion(ArrayList<LearningItem> learningItems) {
        return chooseRandomLearningItemSide(chooseRandomLearningItem(learningItems));
    }

    private LearningItem chooseRandomLearningItem(ArrayList<LearningItem> learningItems) {
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
