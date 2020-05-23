package com.rrm.learnification.settings.learnificationpromptstrategy;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.common.LearningItem;

import java.util.List;

public interface Randomiser {
    LearnificationText randomMixedLearnificationQuestion(List<LearningItem> learningItems);

    LearnificationText randomLeftToRightLearnificationQuestion(List<LearningItem> learningItems);

    LearnificationText randomRightToLeftLearnificationQuestion(List<LearningItem> learningItems);
}
