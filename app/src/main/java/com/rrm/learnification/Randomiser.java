package com.rrm.learnification;

import java.util.List;

interface Randomiser {
    LearnificationText randomLearnificationQuestion(List<LearningItem> learningItems);
}
