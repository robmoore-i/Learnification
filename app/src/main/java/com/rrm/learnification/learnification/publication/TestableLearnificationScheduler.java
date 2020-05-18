package com.rrm.learnification.learnification.publication;

import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ActiveNotificationReader;
import com.rrm.learnification.settings.learnificationdelay.DelayRange;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import java.sql.Time;
import java.util.Optional;

/**
 * You can't write unit tests which refer to classes like LearnificationPublishingService, which is
 * the service class for the jobs this class schedules. In order to enable unit testing, this class
 * implements all the behaviour and branching, while never referring to that class.
 * <p>
 * The wrapping class, AndroidLearnificationScheduler, cannot be unit tested, but includes the
 * references to LearnificationPublishingService, allowing the interface to be the one we really
 * want.
 */
@SuppressWarnings("SameParameterValue")
class TestableLearnificationScheduler {
    private static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final JobScheduler jobScheduler;
    private final ScheduleConfiguration scheduleConfiguration;
    private final AndroidClock androidClock;
    private final ActiveNotificationReader activeNotificationReader;

    private final DelayCalculator delayCalculator = new DelayCalculator();

    TestableLearnificationScheduler(AndroidLogger logger, AndroidClock androidClock, JobScheduler jobScheduler, ScheduleConfiguration scheduleConfiguration, ActiveNotificationReader activeNotificationReader) {
        this.logger = logger;
        this.jobScheduler = jobScheduler;
        this.scheduleConfiguration = scheduleConfiguration;
        this.androidClock = androidClock;
        this.activeNotificationReader = activeNotificationReader;
    }

    void scheduleImminentJob(Class<?> serviceClass) {
        scheduleJob(serviceClass, ScheduleConfiguration.getImminentDelayRange());
    }

    void scheduleJob(Class<?> serviceClass) {
        scheduleJob(serviceClass, scheduleConfiguration.getDelayRange());
    }

    private void scheduleJob(Class<?> serviceClass, DelayRange delayRange) {
        int earliestStartTimeDelayMs = delayRange.earliestStartTimeDelayMs;
        int latestStartTimeDelayMs = delayRange.latestStartTimeDelayMs;

        boolean upcomingLearnificationScheduled = jobScheduler.hasPendingJob(serviceClass, scheduleConfiguration.getDelayRange().earliestStartTimeDelayMs);
        if (upcomingLearnificationScheduled) {
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

    boolean learnificationAvailable() {
        return activeNotificationReader.hasActiveLearnifications();
    }

    Optional<Integer> secondsUntilNextLearnification(Class<?> serviceClass) {
        return jobScheduler.msUntilNextJob(serviceClass)
                .map(l -> l.intValue() / 1000);
    }

    void triggerNext(Class<?> serviceClass) {
        jobScheduler.triggerNext(serviceClass);
    }
}
