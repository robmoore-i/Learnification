package com.rrm.learnification;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LearnificationSchedulerTest {
    private final Class<?> serviceClass = Object.class;
    private final Time nineAm = Time.valueOf("09:00:00");
    private final int earliestStartTimeDelayMs = 10;
    private final int latestStartTimeDelayMs = 20;
    private final PeriodicityRange periodicityRange = new PeriodicityRange(earliestStartTimeDelayMs, latestStartTimeDelayMs);

    private AndroidLogger mockLogger;
    private Scheduler mockScheduler;
    private ScheduleConfiguration stubScheduleConfiguration;
    private ScheduleLog stubScheduleLog;

    @Before
    public void beforeEach() {
        mockLogger = mock(AndroidLogger.class);
        mockScheduler = mock(Scheduler.class);
        stubScheduleConfiguration = mock(ScheduleConfiguration.class);
        stubScheduleLog = mock(ScheduleLog.class);
    }

    @Test
    public void itPassesTheScheduleThroughToTheScheduler() {
        when(stubScheduleConfiguration.getPeriodicityRange()).thenReturn(periodicityRange);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(true);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockScheduler, stubScheduleConfiguration, stubScheduleLog);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockScheduler, times(1)).schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
    }

    @Test
    public void ifScheduleLogSaysTomorrowHasNoLearnificationsThenItSchedulesOne() {
        when(stubScheduleConfiguration.getPeriodicityRange()).thenReturn(periodicityRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockScheduler, stubScheduleConfiguration, stubScheduleLog);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockScheduler, times(2)).schedule(anyInt(), anyInt(), eq(serviceClass));
    }

    @Test
    public void whenSchedulingForTomorrowItLogsTheTime() {
        when(stubScheduleConfiguration.getPeriodicityRange()).thenReturn(periodicityRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockScheduler, stubScheduleConfiguration, stubScheduleLog);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockLogger, times(1)).v(LearnificationScheduler.LOG_TAG, "scheduling learnification for tomorrow at around 09:00:00");
    }
}