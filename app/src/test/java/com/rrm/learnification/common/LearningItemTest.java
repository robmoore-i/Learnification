package com.rrm.learnification.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LearningItemTest {
    @Test
    public void hyphenSeparatedInSingleStringForm() {
        LearningItem learningItem = new LearningItem("left", "right", "default");

        assertEquals(learningItem.toDisplayString().toString(), "left - right");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifNullIsPassedAsLeftItThrowsIllegalArgumentException() {
        new LearningItem(null, "right", "default");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifNullIsPassedAsRightItThrowsIllegalArgumentException() {
        new LearningItem("left", null, "default");
    }
}