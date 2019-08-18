package com.rrm.learnification;

class LearnificationScheduler {
    private static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final Scheduler scheduler;
    private final ScheduleConfiguration scheduleConfiguration;
    private ScheduleLog scheduleLog;

    LearnificationScheduler(AndroidLogger logger, Scheduler scheduler, ScheduleConfiguration scheduleConfiguration, ScheduleLog scheduleLog) {
        this.logger = logger;
        this.scheduler = scheduler;
        this.scheduleConfiguration = scheduleConfiguration;
        this.scheduleLog = scheduleLog;
    }

    @SuppressWarnings("SameParameterValue")
    void scheduleJob(Class<?> serviceClass) {
        PeriodicityRange periodicityRange = scheduleConfiguration.getPeriodicityRange();
        int earliestStartTimeDelayMs = periodicityRange.earliestStartTimeDelayMs;
        int latestStartTimeDelayMs = periodicityRange.latestStartTimeDelayMs;

        logger.v(LOG_TAG, "scheduling notification job in the next " + earliestStartTimeDelayMs + " to " + latestStartTimeDelayMs + "ms");
        scheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);

        if (!scheduleLog.isAnythingScheduledForTomorrow()) {
            scheduler.scheduleTomorrow(serviceClass, scheduleConfiguration.getFirstLearnificationTime());
        }
    }
}
