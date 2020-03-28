package com.rrm.learnification.publication;

import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.settings.learnificationdelay.DelayRange;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
    private final AndroidClock stubAndroidClock = mock(AndroidClock.class);
    private final ResponseNotificationCorrespondent stubResponseNotificationCorrespondent = mock(ResponseNotificationCorrespondent.class);

    @Before
    public void beforeEach() {
        reset(mockLogger, mockJobScheduler, stubScheduleConfiguration, mockJobScheduler, stubAndroidClock, stubResponseNotificationCorrespondent);
        when(mockJobScheduler.hasPendingJob(serviceClass, earliestStartTimeDelayMs)).thenReturn(false);
        when(stubResponseNotificationCorrespondent.hasActiveLearnifications()).thenReturn(false);
        when(stubAndroidClock.now()).thenReturn(fivePmSeptFirst);
    }

    @Test
    public void itPassesTheScheduleThroughToTheScheduler() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(mockJobScheduler.isAnythingScheduledForTomorrow()).thenReturn(true);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, stubAndroidClock, mockJobScheduler, stubScheduleConfiguration, stubResponseNotificationCorrespondent);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, times(1)).schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
    }

    @Test
    public void ifScheduleLogSaysTomorrowHasNoLearnificationsThenItSchedulesOne() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(mockJobScheduler.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, stubAndroidClock, mockJobScheduler, stubScheduleConfiguration, stubResponseNotificationCorrespondent);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, times(2)).schedule(anyInt(), anyInt(), eq(serviceClass));
    }

    @Test
    public void whenSchedulingForTomorrowItLogsTheTime() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(mockJobScheduler.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, stubAndroidClock, mockJobScheduler, stubScheduleConfiguration, stubResponseNotificationCorrespondent);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockLogger, times(1)).i(anyString(), eq("scheduling learnification for tomorrow at around 09:00:00"));
    }

    @Test
    public void whenSchedulingTomorrowItCalculatesTheDelayUsingTheClockAndTheScheduleConfiguration() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(mockJobScheduler.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, stubAndroidClock, mockJobScheduler, stubScheduleConfiguration, stubResponseNotificationCorrespondent);

        learnificationScheduler.scheduleJob(serviceClass);

        int sixteenHoursInMillis = 16 * 60 * 60 * 1000;
        verify(mockJobScheduler, times(1)).schedule(eq(sixteenHoursInMillis), eq(sixteenHoursInMillis + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS)), eq(serviceClass));
    }


    @Test
    public void ifJobSchedulerReportsAPendingJobThenSubsequentJobRequestsAreIgnored() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(mockJobScheduler.isAnythingScheduledForTomorrow()).thenReturn(false);
        when(mockJobScheduler.hasPendingJob(serviceClass, earliestStartTimeDelayMs)).thenReturn(true);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, stubAndroidClock, mockJobScheduler, stubScheduleConfiguration, stubResponseNotificationCorrespondent);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, never()).schedule(anyInt(), anyInt(), eq(serviceClass));
    }

    @Test
    public void ifNotificationManagerReportsAPendingLearnificationThenSubsequentJobRequestsAreIgnored() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(mockJobScheduler.isAnythingScheduledForTomorrow()).thenReturn(false);
        when(stubResponseNotificationCorrespondent.hasActiveLearnifications()).thenReturn(true);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, stubAndroidClock, mockJobScheduler, stubScheduleConfiguration, stubResponseNotificationCorrespondent);

        learnificationScheduler.scheduleJob(serviceClass);

        verify(mockJobScheduler, never()).schedule(anyInt(), anyInt(), eq(serviceClass));
    }

    @Test
    public void itUsesScheduleConfigurationToGetDelayTimeWhenSchedulingAnImminentJob() {
        when(stubScheduleConfiguration.getDelayRange()).thenReturn(delayRange);
        when(stubScheduleConfiguration.getFirstLearnificationTime()).thenReturn(nineAm);
        when(mockJobScheduler.isAnythingScheduledForTomorrow()).thenReturn(false);
        LearnificationScheduler learnificationScheduler = new LearnificationScheduler(mockLogger, stubAndroidClock, mockJobScheduler, stubScheduleConfiguration, stubResponseNotificationCorrespondent);

        learnificationScheduler.scheduleImminentJob(serviceClass);

        verify(mockJobScheduler, times(1)).hasPendingJob(serviceClass, delayRange.earliestStartTimeDelayMs);
    }
}