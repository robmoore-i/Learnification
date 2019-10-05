package com.rrm.learnification.learnification;

import com.rrm.learnification.common.AndroidClock;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.jobscheduler.JobScheduler;
import com.rrm.learnification.notification.NotificationManager;
import com.rrm.learnification.schedulelog.ScheduleLog;
import com.rrm.learnification.settings.DelayRange;
import com.rrm.learnification.settings.ScheduleConfiguration;

import java.sql.Time;

public class LearnificationScheduler {
    public static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final JobScheduler jobScheduler;
    private final ScheduleConfiguration scheduleConfiguration;
    private final ScheduleLog scheduleLog;
    private final AndroidClock androidClock;
    private final NotificationManager notificationManager;

    private final DelayCalculator delayCalculator = new DelayCalculator();

    public LearnificationScheduler(AndroidLogger logger, JobScheduler jobScheduler, ScheduleConfiguration scheduleConfiguration, ScheduleLog scheduleLog, AndroidClock androidClock, NotificationManager notificationManager) {
        this.logger = logger;
        this.jobScheduler = jobScheduler;
        this.scheduleConfiguration = scheduleConfiguration;
        this.scheduleLog = scheduleLog;
        this.androidClock = androidClock;
        this.notificationManager = notificationManager;
    }

    public void scheduleImminentJob(Class<?> serviceClass) {
        scheduleJob(serviceClass, scheduleConfiguration.getImminentDelayRange());
    }

    void scheduleJob(Class<?> serviceClass) {
        scheduleJob(serviceClass, scheduleConfiguration.getDelayRange());
    }

    private void scheduleJob(Class<?> serviceClass, DelayRange delayRange) {
        int earliestStartTimeDelayMs = delayRange.earliestStartTimeDelayMs;
        int latestStartTimeDelayMs = delayRange.latestStartTimeDelayMs;

        if (jobScheduler.hasPendingJob(serviceClass, scheduleConfiguration.getDelayRange().earliestStartTimeDelayMs)) {
            logger.v(LOG_TAG, "ignoring learnification scheduling request because jobScheduler reports that there is one pending");
            return;
        } else if (notificationManager.hasActiveLearnifications()) {
            logger.v(LOG_TAG, "ignoring learnification scheduling request because notificationManager reports that there is one active");
            return;
        }

        logger.v(LOG_TAG, "scheduling learnification in the next " + earliestStartTimeDelayMs + " to " + latestStartTimeDelayMs + "ms");
        jobScheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
        logScheduledLearnification(earliestStartTimeDelayMs);

        if (!scheduleLog.isAnythingScheduledForTomorrow()) {
            scheduleTomorrow(serviceClass);
        }
    }

    private void scheduleTomorrow(Class<?> serviceClass) {
        Time firstLearnificationTime = scheduleConfiguration.getFirstLearnificationTime();
        logger.v(LOG_TAG, "scheduling learnification for tomorrow at around " + firstLearnificationTime.toString());
        int earliestStartTimeDelayMs = delayCalculator.millisBetween(androidClock.now(), firstLearnificationTime);
        int latestStartTimeDelayMs = earliestStartTimeDelayMs + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS);
        jobScheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
        logScheduledLearnification(earliestStartTimeDelayMs);
    }

    private void logScheduledLearnification(int delayMs) {
        scheduleLog.mark(androidClock.now().plusSeconds(delayMs / 1000));
    }
}
