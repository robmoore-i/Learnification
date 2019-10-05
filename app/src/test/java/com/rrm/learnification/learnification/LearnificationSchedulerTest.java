package com.rrm.learnification.learnification;

import com.rrm.learnification.common.AndroidClock;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.jobscheduler.JobScheduler;
import com.rrm.learnification.notification.NotificationManager;
import com.rrm.learnification.schedulelog.ScheduleLog;
import com.rrm.learnification.settings.DelayRange;
import com.rrm.learnification.settings.ScheduleConfiguration;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LearnificationSchedulerTest {
    private final Class<?> serviceClass = Object.class;
    private final Time nineAm = Time.valueOf("09:00:00");
    private final LocalDateTime fivePmSeptFirst = LocalDateTime.of(2019, 9, 1, 17, 0, 0);
    private final int earliestStartTimeDelayMs = 10;
    private final int latestStartTimeDelayMs = 20;
    private final DelayRange delayRange = new DelayRange(earliestStartTimeDelayMs, latestStartTimeDelayMs);

    private final AndroidLogger mockLogger = mock(AndroidLogger.class);
    private final JobScheduler mockJobScheduler = mock(JobScheduler.class);
    private final ScheduleConfiguration stubScheduleConfiguration = mock(ScheduleConfiguration.class);
    private final ScheduleLog stubScheduleLog = mock(ScheduleLog.class);
    private final AndroidClock stubAndroidClock = mock(AndroidClock.class);
    private final NotificationManager stubNotificationManager = mock(NotificationManager.class);

    @Before
    public void beforeEach() {
        reset(mockLogger, mockJobScheduler, stubScheduleConfiguration, stubScheduleLog, stubAndroidClock, stubNotificationManager);
        when(mockJobScheduler.hasPendingJob(serviceClass, earliestStartTimeDelayMs)).thenReturn(false);
        when(stubNotificationManager.hasActiveLearnifications()).thenReturn(false);
        when(stubAndroidClock.now()).thenReturn(fivePmSeptFirst);
    }

    @Test
    public void itPassesTheScheduleThroughToTheScheduler() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(true);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockJobScheduler, stubScheduleConfiguration, stubScheduleLog, stubAndroidClock, stubNotificationManager);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, times(1)).schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
    }

    @Test
    public void ifScheduleLogSaysTomorrowHasNoLearnificationsThenItSchedulesOne() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockJobScheduler, stubScheduleConfiguration, stubScheduleLog, stubAndroidClock, stubNotificationManager);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, times(2)).schedule(anyInt(), anyInt(), eq(serviceClass));
    }

    @Test
    public void whenSchedulingForTomorrowItLogsTheTime() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockJobScheduler, stubScheduleConfiguration, stubScheduleLog, stubAndroidClock, stubNotificationManager);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockLogger, times(1)).v(LearnificationScheduler.LOG_TAG, "scheduling learnification for tomorrow at around 09:00:00");
    }

    @Test
    public void whenSchedulingTomorrowItCalculatesTheDelayUsingTheClockAndTheScheduleConfiguration() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockJobScheduler, stubScheduleConfiguration, stubScheduleLog, stubAndroidClock, stubNotificationManager);

        learnificationScheduler.scheduleJob(serviceClass);

        int sixteenHoursInMillis = 16 * 60 * 60 * 1000;
        verify(mockJobScheduler, times(1)).schedule(eq(sixteenHoursInMillis), eq(sixteenHoursInMillis + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS)), eq(serviceClass));
    }


    @Test
    public void ifJobSchedulerReportsAPendingJobThenSubsequentJobRequestsAreIgnored() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        when(mockJobScheduler.hasPendingJob(serviceClass, earliestStartTimeDelayMs)).thenReturn(true);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockJobScheduler, stubScheduleConfiguration, stubScheduleLog, stubAndroidClock, stubNotificationManager);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, never()).schedule(anyInt(), anyInt(), eq(serviceClass));
    }

    @Test
    public void ifNotificationManagerReportsAPendingLearnificationThenSubsequentJobRequestsAreIgnored() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        when(stubNotificationManager.hasActiveLearnifications()).thenReturn(true);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockJobScheduler, stubScheduleConfiguration, stubScheduleLog, stubAndroidClock, stubNotificationManager);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, never()).schedule(anyInt(), anyInt(), eq(serviceClass));
    }

    @Test
    public void itUsesScheduleConfigurationToGetDelayTimeWhenSchedulingAnImminentJob() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        DelayRange imminentDelayRange = new DelayRange(delayRange.earliestStartTimeDelayMs - 1, delayRange.latestStartTimeDelayMs - 1);
        when(stubScheduleConfiguration.getImminentDelayRange()).thenReturn(imminentDelayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(stubScheduleLog.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, mockJobScheduler, stubScheduleConfiguration, stubScheduleLog, stubAndroidClock, stubNotificationManager);

        learnificationScheduler.scheduleImminentJob(serviceClass);

        verify(mockJobScheduler, times(1)).hasPendingJob(serviceClass, delayRange.earliestStartTimeDelayMs);
    }
}