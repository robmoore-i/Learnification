package com.rrm.learnification.learnification;

import com.rrm.learnification.common.AndroidClock;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.jobscheduler.JobScheduler;
import com.rrm.learnification.schedulelog.ScheduleLog;

import java.sql.Time;

public class LearnificationScheduler {
    public static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final JobScheduler jobScheduler;
    private final ScheduleConfiguration scheduleConfiguration;
    private final ScheduleLog scheduleLog;
    private final AndroidClock androidClock;

    private final DelayCalculator delayCalculator = new DelayCalculator();

    LearnificationScheduler(AndroidLogger logger, JobScheduler jobScheduler, ScheduleConfiguration scheduleConfiguration, ScheduleLog scheduleLog, AndroidClock androidClock) {
        this.logger = logger;
        this.jobScheduler = jobScheduler;
        this.scheduleConfiguration = scheduleConfiguration;
        this.scheduleLog = scheduleLog;
        this.androidClock = androidClock;
    }

    void scheduleJob(Class<?> serviceClass) {
        PeriodicityRange periodicityRange = scheduleConfiguration.getPeriodicityRange();
        int earliestStartTimeDelayMs = periodicityRange.earliestStartTimeDelayMs;
        int latestStartTimeDelayMs = periodicityRange.latestStartTimeDelayMs;

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
