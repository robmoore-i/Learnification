package com.rrm.learnification.common;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LearningItemTest {
    @Test
    public void creatingALearningItemFromAnEmptyLineProducesAnEmptyLearningItem() {
        LearningItem learningItem = LearningItem.fromLine("");
        assertTrue(learningItem.isEmpty());
    }

    @Test
    public void creatingALearningItemFromALineContainingJustADashProducesAnEmptyLearningItem() {
        LearningItem learningItem = LearningItem.fromLine(" - ");
        assertTrue(learningItem.isEmpty());
    }
}