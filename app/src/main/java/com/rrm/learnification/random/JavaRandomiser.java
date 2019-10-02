package com.rrm.learnification.random;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.common.LearningItem;

import java.util.List;
import java.util.Random;

public class JavaRandomiser implements Randomiser {
    private final Random random = new Random();

    @Override
    public LearnificationText randomLearnificationQuestion(List<LearningItem> learningItems) {
        return chooseRandomLearningItemSide(chooseRandomLearningItem(learningItems));
    }

    private LearningItem chooseRandomLearningItem(List<LearningItem> learningItems) {
        int n = random.nextInt(learningItems.size());
        return learningItems.get(n);
    }

    private LearnificationText chooseRandomLearningItemSide(LearningItem learningItem) {
        if (random.nextBoolean()) {
            return new LearnificationText(learningItem.left, learningItem.right, "Learn!");
        } else {
            return new LearnificationText(learningItem.right, learningItem.left, "Learn!");
        }
    }
}
