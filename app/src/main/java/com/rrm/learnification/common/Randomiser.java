package com.rrm.learnification.common;

import java.util.List;

public interface Randomiser {
    LearnificationText randomLearnificationQuestion(List<LearningItem> learningItems);
}
