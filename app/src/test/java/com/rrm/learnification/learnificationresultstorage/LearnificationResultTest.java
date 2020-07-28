package com.rrm.learnification.learnificationresultstorage;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LearnificationResultTest {
    @Test
    public void checksIfSubmittedInLastTwentyFourHours() {
        LearnificationResult learnificationResult = new LearnificationResult(LocalDateTime.of(2020, 7, 28, 10, 3),
                LearnificationResultEvaluation.CORRECT, new LearnificationPrompt("given", "expected"));
        assertTrue(learnificationResult.submittedInLastTwentyFourHours(LocalDateTime.of(2020, 7, 28, 11, 0)));
        assertTrue(learnificationResult.submittedInLastTwentyFourHours(LocalDateTime.of(2020, 7, 29, 9, 0)));
        assertFalse(learnificationResult.submittedInLastTwentyFourHours(LocalDateTime.of(2020, 7, 30, 9, 0)));
    }
}