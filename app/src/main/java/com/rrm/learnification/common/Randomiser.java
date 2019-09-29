package com.rrm.learnification.common;

import java.util.List;

interface Randomiser {
    LearnificationText randomLearnificationQuestion(List<LearningItem> learningItems);
}
