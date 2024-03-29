package com.rrm.learnification.learningitemseteditor.learnificationtoolbar.viewparameters;

import com.rrm.learnification.learnification.publication.LearnificationScheduler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class LearnificationScheduledToolbarViewParametersTest {
    @Test
    public void aFiveSecondDelayIsFormattedCorrectlyIntoTheToolbarTitle() {
        assertEquals("Learnification in 0:00:05", toolbarTitleWithScheduledDelayTimeInSeconds(5));
    }

    @Test
    public void aFiveMinuteDelayIsFormattedCorrectlyIntoTheToolbarTitle() {
        assertEquals("Learnification in 0:05:00", toolbarTitleWithScheduledDelayTimeInSeconds(300));
    }

    @Test
    public void aFiveHourDelayIsFormattedCorrectlyIntoTheToolbarTitle() {
        assertEquals("Learnification in 5:00:00", toolbarTitleWithScheduledDelayTimeInSeconds(18000));
    }

    @Test
    public void handlesNegativeSecondsGracefully() {
        assertEquals("Learnification scheduled", toolbarTitleWithScheduledDelayTimeInSeconds(-1));
    }

    private String toolbarTitleWithScheduledDelayTimeInSeconds(int seconds) {
        return new LearnificationScheduled(mock(LearnificationScheduler.class), seconds).toolbarTitle();
    }
}