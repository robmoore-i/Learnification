package com.rrm.learnification.jobs;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.learnificationdelay.DelayRange;
import com.rrm.learnification.time.AndroidClock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration.getImminentDelayRange;

public class AndroidJobScheduler implements JobScheduler {
    private static final String LOG_TAG = "AndroidJobScheduler";

    static final String TIME_OF_SCHEDULING = "timeOfScheduling";

    private final AndroidLogger logger;
    private final AndroidClock clock;
    private final Context context;
    private final JobIdGenerator jobIdGenerator;
    private final android.app.job.JobScheduler systemJobScheduler;

    public AndroidJobScheduler(AndroidLogger logger, AndroidClock clock, Context context, JobIdGenerator jobIdGenerator) {
        this.logger = logger;
        this.clock = clock;
        this.context = context;
        this.systemJobScheduler = context.getSystemService(android.app.job.JobScheduler.class);
        this.jobIdGenerator = jobIdGenerator;
    }

    @Override
    public void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass) {
        logger.v(LOG_TAG, "scheduling job for serviceClass " + serviceClass.getName() + " in delay range " + earliestStartTimeDelayMs + "-" + latestStartTimeDelayMs);
        JobInfo.Builder builder = new JobInfo.Builder(jobIdGenerator.next(), new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false)
                .setExtras(jobExtras());
        systemJobScheduler.schedule(builder.build());
    }

    @Override
    public boolean hasPendingJob(Class<?> serviceClass, int maxDelayTimeMs) {
        logger.v(LOG_TAG, "checking for pending job with serviceClass " + serviceClass.getName() + " occurring in the next " + maxDelayTimeMs + "ms");
        return pendingJobs().anyMatch(job -> job.willTriggerService(serviceClass) && job.hasDelayTimeNoMoreThan(maxDelayTimeMs));
    }

    @Override
    public Optional<Long> msUntilNextJob(Class<?> serviceClass) {
        return nextJob().map(j -> j.msUntilExecution(clock.now()));
    }

    private Optional<PendingJob> nextJob() {
        LocalDateTime now = clock.now();
        return pendingJobs().min((j1, j2) -> Long.compare(j1.msUntilExecution(now), j2.msUntilExecution(now)));
    }

    @Override
    public boolean isAnythingScheduledForTomorrow() {
        return pendingJobs().anyMatch(j -> (j.scheduledExecutionTime().getDayOfMonth() - clock.now().getDayOfMonth()) == 1);
    }

    @Override
    public void triggerNext(Class<?> serviceClass) {
        Optional<PendingJob> pendingJob = nextJob();
        if (pendingJob.isPresent()) {
            logger.v(LOG_TAG, "triggering next job");
            int id = pendingJob.get().id;
            systemJobScheduler.cancel(id);
            DelayRange imminentDelayRange = getImminentDelayRange();
            schedule(imminentDelayRange.earliestStartTimeDelayMs, imminentDelayRange.latestStartTimeDelayMs, serviceClass);
        } else {
            logger.v(LOG_TAG, "didn't trigger next job because there isn't one");
        }
    }

    private Stream<PendingJob> pendingJobs() {
        return systemJobScheduler
                .getAllPendingJobs()
                .stream()
                .map(PendingJob::fromJobInfo);
    }

    private PersistableBundle jobExtras() {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(TIME_OF_SCHEDULING, clock.now().toString());
        return persistableBundle;
    }
}
