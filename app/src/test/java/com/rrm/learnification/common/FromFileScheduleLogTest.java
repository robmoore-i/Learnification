package com.rrm.learnification.common;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FromFileScheduleLogTest {
    private final LocalDateTime sometime = LocalDateTime.of(2019, 8, 19, 9, 0);

    private AndroidLogger logger = mock(AndroidLogger.class);
    private FileStorageAdaptor mockFileStorage = mock(FileStorageAdaptor.class);
    private Clock stubClock = mock(Clock.class);

    @Before
    public void beforeEach() {
        mockFileStorage = mock(FileStorageAdaptor.class);
        stubClock = mock(Clock.class);
    }

    @Test
    public void ifLatestScheduledLearnificationFileIsEmptyThenNothingIsSaidToBeScheduled() throws IOException {
        when(mockFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenThrow(IOException.class);
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, mockFileStorage, stubClock);

        assertFalse(fromFileScheduleLog.isAnythingScheduledForTomorrow());
    }

    @Test
    public void ifLatestScheduledLearnificationFileHasATimeInItThenSomethingIsSaidToBeScheduled() throws IOException {
        when(mockFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenReturn(Collections.singletonList(sometime.toString()));
        when(stubClock.instant()).thenReturn(sometime.minusHours(10).toInstant(OffsetDateTime.now().getOffset()));
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, mockFileStorage, stubClock);

        assertTrue(fromFileScheduleLog.isAnythingScheduledForTomorrow());
    }

    @Test
    public void ifLatestScheduledLearnificationFileIsTodayThenNothingIsScheduledTomorrow() throws IOException {
        when(mockFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenReturn(Collections.singletonList(sometime.toString()));
        when(stubClock.instant()).thenReturn(sometime.minusHours(2).toInstant(OffsetDateTime.now().getOffset()));
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, mockFileStorage, stubClock);

        assertFalse(fromFileScheduleLog.isAnythingScheduledForTomorrow());
    }

    @Test
    public void markCreatesFileIfItDoesntExist() throws IOException {
        when(mockFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenThrow(IOException.class);
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, mockFileStorage, stubClock);

        fromFileScheduleLog.mark(sometime);

        verify(mockFileStorage, times(1)).overwriteLines(eq(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME), anyList());
    }

    @Test
    public void markWritesCurrentTimeToFile() throws IOException {
        when(mockFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenThrow(IOException.class);
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, mockFileStorage, stubClock);

        fromFileScheduleLog.mark(sometime);

        verify(mockFileStorage, times(1)).overwriteLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME, Collections.singletonList(sometime.toString()));
    }

    @Test
    public void markDoesntOverwriteTheFileIfItContainsALearnificationLaterThanNow() throws IOException {
        when(mockFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenReturn(Collections.singletonList(sometime.plusDays(1).toString()));
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, mockFileStorage, stubClock);

        fromFileScheduleLog.mark(sometime);

        verify(mockFileStorage, times(0)).overwriteLines(anyString(), anyList());
    }

    @Test
    public void markOverwritesTheFileIfItContainsALearnificationBeforeTheNewlyScheduledOne() throws IOException {
        when(mockFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenReturn(Collections.singletonList(sometime.toString()));
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, mockFileStorage, stubClock);

        LocalDateTime scheduledTime = sometime.plusDays(1);
        fromFileScheduleLog.mark(scheduledTime);

        verify(mockFileStorage, times(1)).overwriteLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME, Collections.singletonList(scheduledTime.toString()));
    }
}