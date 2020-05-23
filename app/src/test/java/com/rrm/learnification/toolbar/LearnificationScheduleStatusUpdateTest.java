package com.rrm.learnification.toolbar;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.learnification.publication.LearnificationScheduleStatusUpdate;
import com.rrm.learnification.learnification.publication.LearnificationScheduler;
import com.rrm.learnification.learningitemseteditor.toolbar.ToolbarUpdateListener;
import com.rrm.learnification.logger.AndroidLogger;

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
    private final AndroidLogger mockLogger = mock(AndroidLogger.class);
    private final LearnificationScheduler mockLearnificationScheduler = mock(LearnificationScheduler.class);
    private final ConfigurableButton mockFastForwardScheduleButton = mock(ConfigurableButton.class);
    private final ToolbarUpdateListener mockToolbarUpdateListener = mock(ToolbarUpdateListener.class);

    @Before
    public void beforeEach() {
        reset(mockLogger, mockLearnificationScheduler, mockFastForwardScheduleButton, mockToolbarUpdateListener);
    }

    @Test
    public void doesntLogTheReadyMessageTwice() {
        when(mockLearnificationScheduler.isLearnificationAvailable()).thenReturn(true);
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger,
                mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);
        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);

        verify(mockLogger, times(1)).i(anyString(), contains("'ready'"));
    }

    @Test
    public void logsTwoDifferentMessages() {
        when(mockLearnificationScheduler.isLearnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification()).thenReturn(Optional.of(100)).thenReturn(Optional.empty());
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger,
                mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);
        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);

        verify(mockLogger, times(1)).i(anyString(), contains("'scheduled'"));
        verify(mockLogger, times(1)).i(anyString(), contains("'not scheduled'"));
    }

    @Test
    public void doesntLogTheScheduledMessageTwice() {
        when(mockLearnificationScheduler.isLearnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification()).thenReturn(Optional.of(100)).thenReturn(Optional.of(99));
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger,
                mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);
        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);

        verify(mockLogger, times(1)).i(anyString(), contains("'scheduled'"));
    }

    @Test
    public void doesntLogTheNotScheduledMessageTwice() {
        when(mockLearnificationScheduler.isLearnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification()).thenReturn(Optional.empty()).thenReturn(Optional.empty());
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger,
                mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);
        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);

        verify(mockLogger, times(1)).i(anyString(), contains("'not scheduled'"));
    }

    @Test
    public void doesntLogTheTimeUntilNextLearnificationRepeatedlyAsACountdown() {
        when(mockLearnificationScheduler.isLearnificationAvailable()).thenReturn(false);
        when(mockLearnificationScheduler.secondsUntilNextLearnification()).thenReturn(Optional.of(100)).thenReturn(Optional.of(99));
        LearnificationScheduleStatusUpdate learnificationScheduleStatusUpdate = new LearnificationScheduleStatusUpdate(mockLogger,
                mockLearnificationScheduler, mockFastForwardScheduleButton);

        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);
        learnificationScheduleStatusUpdate.update(mockToolbarUpdateListener);

        verify(mockLogger, times(1)).i(anyString(), contains("next learnification will trigger in"));
    }
}