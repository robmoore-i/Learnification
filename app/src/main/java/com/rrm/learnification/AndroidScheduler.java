package com.rrm.learnification;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import java.sql.Time;
import java.time.LocalDateTime;

class AndroidScheduler implements Scheduler {
    private final DelayCalculator delayCalculator = new DelayCalculator();
    private final Context context;

    AndroidScheduler(Context context) {
        this.context = context;
    }

    @Override
    public void schedule(int earliestStartTimeDelayMs, int latestStartTimeDelayMs, Class<?> serviceClass) {
        JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(context, serviceClass))
                .setMinimumLatency(earliestStartTimeDelayMs)
                .setOverrideDeadline(latestStartTimeDelayMs)
                .setRequiresCharging(false);
        context.getSystemService(JobScheduler.class).schedule(builder.build());
    }

    @Override
    public void scheduleTomorrowMorning(Class<?> serviceClass, Time time) {
        int earliestStartTimeDelayMs = delayCalculator.millisBetween(LocalDateTime.now(), time);
        int latestStartTimeDelayMs = earliestStartTimeDelayMs + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS);
        schedule(earliestStartTimeDelayMs, latestStartTimeDelayMs, serviceClass);
    }
}
