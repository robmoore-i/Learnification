package com.rrm.learnification;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ScheduleConfigurationStorageTest {
    private AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void ifPeriodicityFileDoesntExistThenCreateItWithTheDefaultPeriodicity() throws IOException {
        AndroidInternalStorageAdaptor mockAndroidInternalStorageAdaptor = mock(AndroidInternalStorageAdaptor.class);
        when(mockAndroidInternalStorageAdaptor.doesFileExist(ScheduleConfigurationStorage.FILE_NAME)).thenReturn(false);
        ScheduleConfigurationStorage scheduleConfigurationStorage = new ScheduleConfigurationStorage(logger, mockAndroidInternalStorageAdaptor);

        scheduleConfigurationStorage.getPeriodicityRange();

        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("earliestStartTimeDelayMs=" + ScheduleConfigurationStorage.defaultEarliestStartTimeDelayMs);
        expectedLines.add("latestStartTimeDelayMs=" + ScheduleConfigurationStorage.defaultLatestStartTimeDelayMs);
        verify(mockAndroidInternalStorageAdaptor, times(1)).appendLines(ScheduleConfigurationStorage.FILE_NAME, expectedLines);
    }

    @Test
    public void ifPeriodicityFileExistsThenItReadsTheLinesToReturnThePeriodicity() throws IOException {
        int earliestStartTimeDelayMs = 500000;
        int latestStartTimeDelayMs = 600000;
        AndroidInternalStorageAdaptor mockAndroidInternalStorageAdaptor = mock(AndroidInternalStorageAdaptor.class);
        when(mockAndroidInternalStorageAdaptor.doesFileExist(ScheduleConfigurationStorage.FILE_NAME)).thenReturn(true);
        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("earliestStartTimeDelayMs=" + earliestStartTimeDelayMs);
        expectedLines.add("latestStartTimeDelayMs=" + latestStartTimeDelayMs);
        when(mockAndroidInternalStorageAdaptor.readLines(ScheduleConfigurationStorage.FILE_NAME)).thenReturn(expectedLines);
        ScheduleConfigurationStorage scheduleConfigurationStorage = new ScheduleConfigurationStorage(logger, mockAndroidInternalStorageAdaptor);

        PeriodicityRange periodicityRange = scheduleConfigurationStorage.getPeriodicityRange();

        assertThat(periodicityRange.earliestStartTimeDelayMs, equalTo(earliestStartTimeDelayMs));
        assertThat(periodicityRange.latestStartTimeDelayMs, equalTo(latestStartTimeDelayMs));
    }
}