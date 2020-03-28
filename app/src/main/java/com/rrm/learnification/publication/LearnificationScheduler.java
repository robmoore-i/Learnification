package com.rrm.learnification.publication;

import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.settings.learnificationdelay.DelayRange;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import java.sql.Time;
import java.util.Optional;

public class LearnificationScheduler {
    private static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final JobScheduler jobScheduler;
    private final ScheduleConfiguration scheduleConfiguration;
    private final AndroidClock androidClock;
    private final ResponseNotificationCorrespondent responseNotificationCorrespondent;

    private final DelayCalculator delayCalculator = new DelayCalculator();

    public LearnificationScheduler(AndroidLogger logger, AndroidClock androidClock, JobScheduler jobScheduler, ScheduleConfiguration scheduleConfiguration, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        this.logger = logger;
        this.jobScheduler = jobScheduler;
        this.scheduleConfiguration = scheduleConfiguration;
        this.androidClock = androidClock;
        this.responseNotificationCorrespondent = responseNotificationCorrespondent;
    }

    public void scheduleImminentJob(Class<?> serviceClass) {
        scheduleJob(serviceClass, ScheduleConfiguration.getImminentDelayRange());
    }

    public void scheduleJob(Class<?> serviceClass) {
        scheduleJob(serviceClass, scheduleConfiguration.getDelayRange());
    }

    private void scheduleJob(Class<?> serviceClass, DelayRange delayRange) {
        int earliestStartTimeDelayMs = delayRange.earliestStartTimeDelayMs;
        int latestStartTimeDelayMs = delayRange.latestStartTimeDelayMs;

        if (upcomingLearnificationScheduled(serviceClass)) {
            logger.i(LOG_TAG, "ignoring learnification scheduling request because jobScheduler reports that there is one pending");
            return;
        } else if (learnificationAvailable()) {
            logger.i(LOG_TAG, "ignoring learnification scheduling request because responseNotificationCorrespondent reports that there is one active");
            return;
        }

        logger.i(LOG_TAG, "scheduling learnification in the next " + earliestStartTimeDelayMs + " to " + latestStartTimeDelayMs + "ms");
        jobScheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);

        if (!jobScheduler.isAnythingScheduledForTomorrow()) {
            scheduleTomorrow(serviceClass);
        }
    }

    private void scheduleTomorrow(Class<?> serviceClass) {
        Time firstLearnificationTime = scheduleConfiguration.getFirstLearnificationTime();
        logger.i(LOG_TAG, "scheduling learnification for tomorrow at around " + firstLearnificationTime.toString());
        int earliestStartTimeDelayMs = delayCalculator.millisBetween(androidClock.now(), firstLearnificationTime);
        int latestStartTimeDelayMs = earliestStartTimeDelayMs + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS);
        jobScheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
    }

    public boolean learnificationAvailable() {
        return responseNotificationCorrespondent.hasActiveLearnifications();
    }

    private boolean upcomingLearnificationScheduled(Class<?> serviceClass) {
        return jobScheduler.hasPendingJob(serviceClass, scheduleConfiguration.getDelayRange().earliestStartTimeDelayMs);
    }

    public Optional<Integer> secondsUntilNextLearnification(Class<?> serviceClass) {
        return jobScheduler.msUntilNextJob(serviceClass)
                .map(l -> l.intValue() / 1000);
    }

    public void triggerNext(Class<?> serviceClass) {
        jobScheduler.triggerNext(serviceClass);
    }
}
