package com.rrm.learnification.dailyreport.publication;

import com.rrm.learnification.jobs.DelayCalculator;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import java.sql.Time;

@SuppressWarnings("SameParameterValue")
public class TestableDailyReportScheduler {
    private static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final JobScheduler jobScheduler;
    private final ScheduleConfiguration scheduleConfiguration;
    private final AndroidClock clock;
    private final DelayCalculator delayCalculator = new DelayCalculator();

    public TestableDailyReportScheduler(AndroidLogger logger, AndroidClock clock, JobScheduler jobScheduler,
                                        ScheduleConfiguration scheduleConfiguration) {
        this.logger = logger;
        this.jobScheduler = jobScheduler;
        this.scheduleConfiguration = scheduleConfiguration;
        this.clock = clock;
    }

    void scheduleJob(Class<?> serviceClass) {
        boolean nextDailyReportIsScheduledAlready = jobScheduler.anyJobMatches(pendingJob -> pendingJob.isForService(serviceClass));
        if (!nextDailyReportIsScheduledAlready) {
            Time dailyReportTime = scheduleConfiguration.getDailyReportTime();
            logger.i(LOG_TAG, "scheduling learnification for tomorrow at around " + dailyReportTime.toString());
            int earliestStartTimeDelayMs = delayCalculator.millisBetween(clock.now(), dailyReportTime);
            int latestStartTimeDelayMs = earliestStartTimeDelayMs + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS);
            jobScheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
        }
    }
}
