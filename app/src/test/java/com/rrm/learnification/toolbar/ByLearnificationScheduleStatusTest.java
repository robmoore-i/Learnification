package com.rrm.learnification.toolbar;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ByLearnificationScheduleStatusTest {
    private AndroidLogger mockLogger = mock(AndroidLogger.class);
    private LearnificationScheduler mockLearnificationScheduler = mock(LearnificationScheduler.class);
    private ConfigurableButton mockFastForwardScheduleButton = mock(ConfigurableButton.class);
    private ToolbarView mockToolbarView = mock(ToolbarView.class);

    @Before
    public void beforeEach() {
        reset(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton, mockToolbarView);
    }

    @Test
    public void doesntLogTheSameMessageTwice() {
        when(mockLearnificationScheduler.learnificationAvailable()).thenReturn(true);
        ByLearnificationScheduleStatus byLearnificationScheduleStatus = new ByLearnificationScheduleStatus(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton);

        byLearnificationScheduleStatus.update(mockToolbarView);
        byLearnificationScheduleStatus.update(mockToolbarView);

        verify(mockLogger, times(1)).v(anyString(), contains("'ready'"));
    }

    @Test
    public void logsTwoDifferentMessages() {
        when(mockLearnificationScheduler.learnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class)).thenReturn(Optional.of(100)).thenReturn(Optional.empty());
        ByLearnificationScheduleStatus byLearnificationScheduleStatus = new ByLearnificationScheduleStatus(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton);

        byLearnificationScheduleStatus.update(mockToolbarView);
        byLearnificationScheduleStatus.update(mockToolbarView);

        verify(mockLogger, times(1)).v(anyString(), contains("'scheduled'"));
        verify(mockLogger, times(1)).v(anyString(), contains("'not scheduled'"));
    }

    @Test
    public void alwaysUpdatesToolbarViewIfStatusIsScheduled() {
        when(mockLearnificationScheduler.learnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class)).thenReturn(Optional.of(100)).thenReturn(Optional.of(99));
        ByLearnificationScheduleStatus byLearnificationScheduleStatus = new ByLearnificationScheduleStatus(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton);

        byLearnificationScheduleStatus.update(mockToolbarView);
        byLearnificationScheduleStatus.update(mockToolbarView);

        verify(mockLogger, times(2)).v(anyString(), contains("'scheduled'"));
    }
}