package com.rrm.learnification.common;

import android.app.job.JobInfo;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.jobscheduler.JobIdGenerator;
import com.rrm.learnification.jobscheduler.JobScheduler;
import com.rrm.learnification.learnification.LearnificationSchedulerService;
import com.rrm.learnification.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("ConstantConditions")
@RunWith(AndroidJUnit4.class)
public class JobSchedulerTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private JobScheduler jobScheduler;
    private android.app.job.JobScheduler systemScheduler;

    @Before
    public void beforeEach() {
        jobScheduler = activityTestRule.getActivity().getJobScheduler();
        systemScheduler = activityTestRule.getActivity().getSystemService(android.app.job.JobScheduler.class);
    }

    @After
    public void afterEach() {
        systemScheduler.cancelAll();
    }

    @Test
    public void itSchedulesAJobsWithTheCorrectParameters() {
        jobScheduler.schedule(10000, 20000, LearnificationSchedulerService.class);

        JobInfo pendingJob = systemScheduler.getPendingJob(JobIdGenerator.getInstance().lastJobId());
        assertThat(pendingJob.getMinLatencyMillis(), equalTo(10000L));
        assertThat(pendingJob.getMaxExecutionDelayMillis(), equalTo(20000L));
        assertThat(pendingJob.getService().getClassName(), equalTo("com.rrm.learnification.learnification.LearnificationSchedulerService"));
    }
}
