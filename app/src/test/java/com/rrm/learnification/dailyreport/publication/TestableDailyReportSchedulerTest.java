package com.rrm.learnification.dailyreport.publication;

import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import org.junit.Test;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestableDailyReportSchedulerTest {
    private final Class<?> serviceClass = Object.class;

    private final Time ninePm = Time.valueOf("21:00:00");
    private final LocalDateTime eightAmJulyTwentyFirst = LocalDateTime.of(2020, 7, 21, 8, 0, 0);

    private final AndroidLogger mockLogger = mock(AndroidLogger.class);
    private final JobScheduler mockJobScheduler = mock(JobScheduler.class);
    private final ScheduleConfiguration stubScheduleConfiguration = mock(ScheduleConfiguration.class);
    private final AndroidClock stubAndroidClock = mock(AndroidClock.class);
    private int thirteenHoursInMillis = 13 * 60 * 60 * 1000;

    @Test
    public void itPassesTheScheduleThroughToTheScheduler() {
        when(stubScheduleConfiguration.getDailyReportTime()).thenReturn(ninePm);
        when(stubAndroidClock.now()).thenReturn(eightAmJulyTwentyFirst);
        when(mockJobScheduler.msUntilNextJob(serviceClass)).thenReturn(Optional.empty());
        TestableDailyReportScheduler dailyReportScheduler = new TestableDailyReportScheduler(mockLogger, stubAndroidClock,
                mockJobScheduler, stubScheduleConfiguration);

        dailyReportScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, times(1)).schedule(eq(thirteenHoursInMillis),
                eq(thirteenHoursInMillis + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS)), eq(serviceClass));
    }

    @Test
    public void ifJobSchedulerReportsAPendingJobThenSubsequentJobRequestsAreIgnored() {
        when(stubScheduleConfiguration.getDailyReportTime()).thenReturn(ninePm);
        when(stubAndroidClock.now()).thenReturn(eightAmJulyTwentyFirst);
        when(mockJobScheduler.msUntilNextJob(serviceClass)).thenReturn(Optional.of((long) thirteenHoursInMillis));
        TestableDailyReportScheduler dailyReportScheduler = new TestableDailyReportScheduler(mockLogger, stubAndroidClock,
                mockJobScheduler, stubScheduleConfiguration);

        dailyReportScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, never()).schedule(anyInt(), anyInt(), eq(serviceClass));
    }
}