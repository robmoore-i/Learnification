package com.rrm.learnification.learnification.publication;

import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ActiveNotificationReader;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import java.util.Optional;

public class AndroidLearnificationScheduler implements LearnificationScheduler {
    private final TestableLearnificationScheduler learnificationScheduler;
    private final Class<LearnificationPublishingService> learnificationPublicationClass = LearnificationPublishingService.class;

    public AndroidLearnificationScheduler(AndroidLogger logger, AndroidClock androidClock, JobScheduler jobScheduler,
                                          ScheduleConfiguration scheduleConfiguration, ActiveNotificationReader activeNotificationReader) {
        this.learnificationScheduler = new TestableLearnificationScheduler(logger, androidClock, jobScheduler, scheduleConfiguration, activeNotificationReader);
    }

    @Override
    public void scheduleLearnification() {
        learnificationScheduler.scheduleJob(learnificationPublicationClass);
    }

    @Override
    public void scheduleImminentLearnification() {
        learnificationScheduler.scheduleImminentJob(learnificationPublicationClass);
    }

    @Override
    public void triggerNextLearnification() {
        learnificationScheduler.triggerNext(learnificationPublicationClass);
    }

    @Override
    public boolean isLearnificationAvailable() {
        return learnificationScheduler.learnificationAvailable();
    }

    @Override
    public Optional<Integer> secondsUntilNextLearnification() {
        return learnificationScheduler.secondsUntilNextLearnification(learnificationPublicationClass);
    }
}
