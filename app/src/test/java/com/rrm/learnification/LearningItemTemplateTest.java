package com.rrm.learnification;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LearningItemTemplateTest {
    @Test
    public void canCreateLearningItemOnLeft() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");

        LearningItem learningItem = learningItemTemplate.build("Egypt", "Cairo");

        assertThat(learningItem.left, equalTo("What is the capital city of Egypt?"));
    }

    @Test
    public void canCreateLearningItemOnRight() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city of");

        LearningItem learningItem = learningItemTemplate.build("Egypt", "Cairo");

        assertThat(learningItem.right, equalTo("Which country has the capital city of Cairo?"));
    }
}
