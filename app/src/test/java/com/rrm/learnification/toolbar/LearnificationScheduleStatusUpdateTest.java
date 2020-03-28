package com.rrm.learnification.toolbar;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.logger.AndroidLogger;
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

public class LearnificationScheduleStatusUpdateTest {
    private AndroidLogger mockLogger = mock(AndroidLogger.class);
    private LearnificationScheduler mockLearnificationScheduler = mock(LearnificationScheduler.class);
    private ConfigurableButton mockFastForwardScheduleButton = mock(ConfigurableButton.class);
    private ToolbarView mockToolbarView = mock(ToolbarView.class);

    @Before
    public void beforeEach() {
        reset(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton, mockToolbarView);
    }

    @Test
    public void doesntLogTheReadyMessageTwice() {
        when(mockLearnificationScheduler.learnificationAvailable()).thenReturn(true);
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarView);
        learnificationScheduleStatusUpdate.update(mockToolbarView);

        verify(mockLogger, times(1)).i(anyString(), contains("'ready'"));
    }

    @Test
    public void logsTwoDifferentMessages() {
        when(mockLearnificationScheduler.learnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class)).thenReturn(Optional.of(100)).thenReturn(Optional.empty());
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarView);
        learnificationScheduleStatusUpdate.update(mockToolbarView);

        verify(mockLogger, times(1)).i(anyString(), contains("'scheduled'"));
        verify(mockLogger, times(1)).i(anyString(), contains("'not scheduled'"));
    }

    @Test
    public void doesntLogTheScheduledMessageTwice() {
        when(mockLearnificationScheduler.learnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class)).thenReturn(Optional.of(100)).thenReturn(Optional.of(99));
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarView);
        learnificationScheduleStatusUpdate.update(mockToolbarView);

        verify(mockLogger, times(1)).i(anyString(), contains("'scheduled'"));
    }

    @Test
    public void doesntLogTheNotScheduledMessageTwice() {
        when(mockLearnificationScheduler.learnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class)).thenReturn(Optional.empty()).thenReturn(Optional.empty());
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarView);
        learnificationScheduleStatusUpdate.update(mockToolbarView);

        verify(mockLogger, times(1)).i(anyString(), contains("'not scheduled'"));
    }

    @Test
    public void doesntLogTheTimeUntilNextLearnificationRepeatedlyAsACountdown() {
        when(mockLearnificationScheduler.learnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class)).thenReturn(Optional.of(100)).thenReturn(Optional.of(99));
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarView);
        learnificationScheduleStatusUpdate.update(mockToolbarView);

        verify(mockLogger, times(1)).i(anyString(), contains("next learnification will trigger in"));
    }
}