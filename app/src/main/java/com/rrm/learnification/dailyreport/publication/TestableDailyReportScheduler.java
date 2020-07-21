package com.rrm.learnification.dailyreport.publication;

import com.rrm.learnification.jobs.DelayCalculator;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;
import com.rrm.learnification.time.AndroidClock;

import java.sql.Time;
import java.util.Optional;

@SuppressWarnings("SameParameterValue")
public class TestableDailyReportScheduler {
    private static final String LOG_TAG = "LearnificationScheduler";

    private final AndroidLogger logger;
    private final JobScheduler jobScheduler;
    private final ScheduleConfiguration scheduleConfiguration;
    private final AndroidClock androidClock;

    private final DelayCalculator delayCalculator = new DelayCalculator();

    public TestableDailyReportScheduler(
            AndroidLogger logger, AndroidClock androidClock,
            JobScheduler jobScheduler, ScheduleConfiguration scheduleConfiguration) {
        this.logger = logger;
        this.jobScheduler = jobScheduler;
        this.scheduleConfiguration = scheduleConfiguration;
        this.androidClock = androidClock;
    }

    void scheduleJob(Class<?> serviceClass) {
        if (todayDailyReportIsScheduled(serviceClass)) {
            logger.i(LOG_TAG, "ignoring daily report scheduling request because jobScheduler reports that there is one pending");
            return;
        }

        Time dailyReportTime = scheduleConfiguration.getDailyReportTime();
        logger.i(LOG_TAG, "scheduling learnification for tomorrow at around " + dailyReportTime.toString());
        int earliestStartTimeDelayMs = delayCalculator.millisBetween(androidClock.now(), dailyReportTime);
        int latestStartTimeDelayMs = earliestStartTimeDelayMs + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS);
        jobScheduler.schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
    }

    /*
    Returns true if at the next daily report time, there is a daily report scheduled.
    */
    private boolean todayDailyReportIsScheduled(Class<?> serviceClass) {
        Optional<Long> msUntilNextDailyReport = jobScheduler.msUntilNextJob(serviceClass);
        if (!msUntilNextDailyReport.isPresent()) {
            return false;
        }
        Time dailyReportTime = scheduleConfiguration.getDailyReportTime();
        int expectedMsUntilNextDailyReport = delayCalculator.millisBetween(androidClock.now(), dailyReportTime);
        int actualMsUntilNextDailyReport = msUntilNextDailyReport.get().intValue();
        int differenceBetweenActualAndExpectedNextDailyReportTimes = Math.abs(expectedMsUntilNextDailyReport - actualMsUntilNextDailyReport);
        int maxExpectedDelayInDailyReportNotificationExecution = 1 + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS);
        return differenceBetweenActualAndExpectedNextDailyReportTimes <= maxExpectedDelayInDailyReportNotificationExecution;
    }
}
