package com.rrm.learnification;

class LearnificationScheduler {
    private static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final AndroidJobScheduler androidJobScheduler;
    private final ScheduleConfiguration scheduleConfiguration;

    LearnificationScheduler(AndroidLogger logger, AndroidJobScheduler androidJobScheduler, ScheduleConfiguration scheduleConfiguration) {
        this.logger = logger;
        this.androidJobScheduler = androidJobScheduler;
        this.scheduleConfiguration = scheduleConfiguration;
    }

    void scheduleJob() {
        PeriodicityRange periodicityRange = scheduleConfiguration.getPeriodicityRange();
        int earliestStartTimeDelayMs = periodicityRange.earliestStartTimeDelayMs;
        int latestStartTimeDelayMs = periodicityRange.latestStartTimeDelayMs;

        logger.v(LOG_TAG, "scheduling notification job in the next " + earliestStartTimeDelayMs + " to " + latestStartTimeDelayMs + "ms");
        androidJobScheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, LearnificationSchedulerService.class);
    }
}
