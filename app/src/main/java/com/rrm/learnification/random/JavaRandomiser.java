package com.rrm.learnification.random;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.common.LearningItem;

import java.util.List;
import java.util.Random;

public class JavaRandomiser implements Randomiser {
    private final Random random = new Random();

    @Override
    public LearnificationText randomMixedLearnificationQuestion(List<LearningItem> learningItems) {
        return randomSideText(chooseRandomLearningItem(learningItems));
    }

    @Override
    public LearnificationText randomLeftToRightLearnificationQuestion(List<LearningItem> learningItems) {
        return leftToRightText(chooseRandomLearningItem(learningItems));
    }

    @Override
    public LearnificationText randomRightToLeftLearnificationQuestion(List<LearningItem> learningItems) {
        return rightToLeftText(chooseRandomLearningItem(learningItems));
    }

    private LearningItem chooseRandomLearningItem(List<LearningItem> learningItems) {
        int n = random.nextInt(learningItems.size());
        return learningItems.get(n);
    }

    private LearnificationText randomSideText(LearningItem learningItem) {
        if (random.nextBoolean()) {
            return leftToRightText(learningItem);
        } else {
            return rightToLeftText(learningItem);
        }
    }

    private LearnificationText rightToLeftText(LearningItem learningItem) {
        return new LearnificationText(learningItem.right, learningItem.left);
    }

    private LearnificationText leftToRightText(LearningItem learningItem) {
        return new LearnificationText(learningItem.left, learningItem.right);
    }
}
