package com.rrm.learnification.learnification.publication;

import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.ResponseNotificationCorrespondent;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import java.util.Optional;

public class AndroidLearnificationScheduler implements LearnificationScheduler {
    private final TestableLearnificationScheduler learnificationScheduler;

    public AndroidLearnificationScheduler(AndroidLogger logger, AndroidClock androidClock, JobScheduler jobScheduler, ScheduleConfiguration scheduleConfiguration, ResponseNotificationCorrespondent responseNotificationCorrespondent) {
        this.learnificationScheduler = new TestableLearnificationScheduler(logger, androidClock, jobScheduler, scheduleConfiguration, responseNotificationCorrespondent);
    }

    @Override
    public void scheduleJob() {
        learnificationScheduler.scheduleJob(LearnificationPublishingService.class);
    }

    @Override
    public void scheduleImminentJob() {
        learnificationScheduler.scheduleImminentJob(LearnificationPublishingService.class);
    }

    @Override
    public void triggerNext() {
        learnificationScheduler.triggerNext(LearnificationPublishingService.class);
    }

    @Override
    public boolean learnificationAvailable() {
        return learnificationScheduler.learnificationAvailable();
    }

    @Override
    public Optional<Integer> secondsUntilNextLearnification() {
        return learnificationScheduler.secondsUntilNextLearnification(LearnificationPublishingService.class);
    }
}
