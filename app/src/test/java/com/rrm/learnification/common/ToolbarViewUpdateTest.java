package com.rrm.learnification.common;

import com.rrm.learnification.publication.LearnificationScheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ToolbarViewUpdateTest {
    private final LearnificationScheduler mockLearnificationScheduler = mock(LearnificationScheduler.class);
    private final ToolbarView mockToolbarView = mock(ToolbarView.class);
    private final AndroidLogger logger = mock(AndroidLogger.class);

    @Before
    public void beforeEach() {
        reset(mockLearnificationScheduler, mockToolbarView, logger);
    }

    @Test
    public void itFormatsFiveSecondDelay() {
        when(mockLearnificationScheduler.secondsUntilNextLearnification(any())).thenReturn(Optional.of(5));
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ToolbarViewUpdate toolbarViewUpdate = new ToolbarViewUpdate(logger, mockLearnificationScheduler);

        toolbarViewUpdate.update(mockToolbarView);

        verify(mockToolbarView, times(1)).updateActivityTitle(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), equalTo("Learnification in 0:00:05"));
    }

    @Test
    public void itFormatsFiveMinuteDelay() {
        when(mockLearnificationScheduler.secondsUntilNextLearnification(any())).thenReturn(Optional.of(300));
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ToolbarViewUpdate toolbarViewUpdate = new ToolbarViewUpdate(logger, mockLearnificationScheduler);

        toolbarViewUpdate.update(mockToolbarView);

        verify(mockToolbarView, times(1)).updateActivityTitle(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), equalTo("Learnification in 0:05:00"));
    }

    @Test
    public void itFormatsFiveHourDelay() {
        when(mockLearnificationScheduler.secondsUntilNextLearnification(any())).thenReturn(Optional.of(18000));
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ToolbarViewUpdate toolbarViewUpdate = new ToolbarViewUpdate(logger, mockLearnificationScheduler);

        toolbarViewUpdate.update(mockToolbarView);

        verify(mockToolbarView, times(1)).updateActivityTitle(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), equalTo("Learnification in 5:00:00"));
    }
}