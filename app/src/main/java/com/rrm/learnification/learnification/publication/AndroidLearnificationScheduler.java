package com.rrm.learnification.learnification.publication;

import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ActiveNotificationReader;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import java.util.Optional;

public class AndroidLearnificationScheduler implements LearnificationScheduler {
    private final TestableLearnificationScheduler learnificationScheduler;

    public AndroidLearnificationScheduler(AndroidLogger logger, AndroidClock androidClock, JobScheduler jobScheduler,
                                          ScheduleConfiguration scheduleConfiguration, ActiveNotificationReader activeNotificationReader) {
        this.learnificationScheduler = new TestableLearnificationScheduler(logger, androidClock, jobScheduler, scheduleConfiguration, activeNotificationReader);
    }

    @Override
    public void scheduleLearnification() {
        learnificationScheduler.scheduleJob(LearnificationPublishingService.class);
    }

    @Override
    public void scheduleImminentLearnification() {
        learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);
    }

    @Override
    public void triggerNextLearnification() {
        learnificationScheduler.triggerNext(LearnificationPublishingService.class);
    }

    @Override
    public boolean isLearnificationAvailable() {
        return learnificationScheduler.learnificationAvailable();
    }

    @Override
    public Optional<Integer> secondsUntilNextLearnification() {
        return learnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class);
    }
}
