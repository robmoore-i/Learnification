package com.rrm.learnification;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LearnificationSchedulerTest {
    private final Class<?> serviceClass = Object.class;
    private final Time nineAm = Time.valueOf("09:00:00");

    private final AndroidLogger logger = mock(AndroidLogger.class);

    private Scheduler mockScheduler;
    private ScheduleConfiguration stubScheduleConfiguration;
    private ScheduleLog stubScheduleLog;

    @Before
    public void beforeEach() {
        mockScheduler = mock(Scheduler.class);
        stubScheduleConfiguration = mock(ScheduleConfiguration.class);
        stubScheduleLog = mock(ScheduleLog.class);
    }

    @Test
    public void itPassesTheScheduleThroughToTheScheduler() {
        when(stubScheduleConfiguration.getPeriodicityRange()).thenReturn(new PeriodicityRange(10, 20));
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, mockScheduler, stubScheduleConfiguration, stubScheduleLog);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockScheduler, times(1)).schedule(10, 20, serviceClass);
    }

    @Test
    public void ifScheduleLogSaysTomorrowHasNoLearnificationsThenItSchedulesOne() {
        when(stubScheduleConfiguration.getPeriodicityRange()).thenReturn(new PeriodicityRange(10, 20));
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(logger, mockScheduler, stubScheduleConfiguration, stubScheduleLog);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockScheduler, times(1)).scheduleTomorrowMorning(serviceClass, nineAm);
    }
}