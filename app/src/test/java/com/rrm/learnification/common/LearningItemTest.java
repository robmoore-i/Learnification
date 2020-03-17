package com.rrm.learnification.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LearningItemTest {
    @Test
    public void hyphenSeparatedInSingleStringForm() {
        LearningItem learningItem = new LearningItem("left", "right");

        assertEquals(learningItem.toDisplayString(), "left - right");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifNullIsPassedAsLeftItThrowsIllegalArgumentException() {
        new LearningItem(null, "right");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifNullIsPassedAsRightItThrowsIllegalArgumentException() {
        new LearningItem("left", null);
    }
}