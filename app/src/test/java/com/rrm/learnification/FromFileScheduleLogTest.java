package com.rrm.learnification;

import org.junit.Test;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FromFileScheduleLogTest {
    private AndroidLogger logger = mock(AndroidLogger.class);
    private FileStorageAdaptor stubFileStorage = mock(FileStorageAdaptor.class);
    private Clock stubClock = mock(Clock.class);

    @Test
    public void ifLatestScheduledLearnificationFileIsEmptyThenNothingIsSaidToBeScheduled() throws IOException {
        when(stubFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenThrow(IOException.class);
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, stubFileStorage, stubClock);

        assertFalse(fromFileScheduleLog.isAnythingScheduledForTomorrow());
    }

    @Test
    public void ifLatestScheduledLearnificationFileHasATimeInItThenSomethingIsSaidToBeScheduled() throws IOException {
        LocalDateTime latestScheduledLearnificationTime = LocalDateTime.of(2019, 8, 19, 9, 0);
        when(stubFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenReturn(Collections.singletonList(latestScheduledLearnificationTime.toString()));
        when(stubClock.instant()).thenReturn(latestScheduledLearnificationTime.minusHours(10).toInstant(OffsetDateTime.now().getOffset()));
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, stubFileStorage, stubClock);

        assertTrue(fromFileScheduleLog.isAnythingScheduledForTomorrow());
    }

    @Test
    public void ifLatestScheduledLearnificationFileIsTodayThenNothingIsScheduledTomorrow() throws IOException {
        LocalDateTime latestScheduledLearnificationTime = LocalDateTime.of(2019, 8, 19, 9, 0);
        when(stubFileStorage.readLines(FromFileScheduleLog.LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME)).thenReturn(Collections.singletonList(latestScheduledLearnificationTime.toString()));
        when(stubClock.instant()).thenReturn(latestScheduledLearnificationTime.minusHours(2).toInstant(OffsetDateTime.now().getOffset()));
        FromFileScheduleLog fromFileScheduleLog = new FromFileScheduleLog(logger, stubFileStorage, stubClock);

        assertFalse(fromFileScheduleLog.isAnythingScheduledForTomorrow());
    }
}