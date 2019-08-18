package com.rrm.learnification;

import java.sql.Time;
import java.time.LocalDateTime;

class LearnificationScheduler {
    static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final Scheduler scheduler;
    private final ScheduleConfiguration scheduleConfiguration;
    private final ScheduleLog scheduleLog;

    private final DelayCalculator delayCalculator = new DelayCalculator();

    LearnificationScheduler(AndroidLogger logger, Scheduler scheduler, ScheduleConfiguration scheduleConfiguration, ScheduleLog scheduleLog) {
        this.logger = logger;
        this.scheduler = scheduler;
        this.scheduleConfiguration = scheduleConfiguration;
        this.scheduleLog = scheduleLog;
    }

    void scheduleJob(Class<?> serviceClass) {
        PeriodicityRange periodicityRange = scheduleConfiguration.getPeriodicityRange();
        int earliestStartTimeDelayMs = periodicityRange.earliestStartTimeDelayMs;
        int latestStartTimeDelayMs = periodicityRange.latestStartTimeDelayMs;

        logger.v(LOG_TAG, "scheduling learnification in the next " + earliestStartTimeDelayMs + " to " + latestStartTimeDelayMs + "ms");
        scheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
        logScheduledLearnification(earliestStartTimeDelayMs);

        if (!scheduleLog.isAnythingScheduledForTomorrow()) {
            scheduleTomorrow(serviceClass);
        }
    }

    private void scheduleTomorrow(Class<?> serviceClass) {
        Time firstLearnificationTime = scheduleConfiguration.getFirstLearnificationTime();
        logger.v(LOG_TAG, "scheduling learnification for tomorrow at around " + firstLearnificationTime.toString());
        int earliestStartTimeDelayMs = delayCalculator.millisBetween(now(), firstLearnificationTime);
        int latestStartTimeDelayMs = earliestStartTimeDelayMs + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS);
        scheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
        logScheduledLearnification(earliestStartTimeDelayMs);
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private void logScheduledLearnification(int delayMs) {
        scheduleLog.mark(now().plusSeconds(delayMs / 1000));
    }
}
