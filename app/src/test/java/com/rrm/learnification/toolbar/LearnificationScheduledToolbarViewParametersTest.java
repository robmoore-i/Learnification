package com.rrm.learnification.toolbar;

import com.rrm.learnification.publication.LearnificationScheduler;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class LearnificationScheduledToolbarViewParametersTest {
    @Test
    public void aFiveSecondDelayIsFormattedCorrectlyIntoTheToolbarTitle() {
        assertThat(toolbarTitleWithScheduledDelayTimeInSeconds(5), equalTo("Learnification in 0:00:05"));
    }

    @Test
    public void aFiveMinuteDelayIsFormattedCorrectlyIntoTheToolbarTitle() {
        assertThat(toolbarTitleWithScheduledDelayTimeInSeconds(300), equalTo("Learnification in 0:05:00"));
    }

    @Test
    public void aFiveHourDelayIsFormattedCorrectlyIntoTheToolbarTitle() {
        assertThat(toolbarTitleWithScheduledDelayTimeInSeconds(18000), equalTo("Learnification in 5:00:00"));
    }

    private String toolbarTitleWithScheduledDelayTimeInSeconds(int seconds) {
        return new ToolbarViewParameters.LearnificationScheduled(mock(LearnificationScheduler.class), seconds).toolbarTitle();
    }
}