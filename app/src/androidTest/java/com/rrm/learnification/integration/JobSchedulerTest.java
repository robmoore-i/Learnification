package com.rrm.learnification.integration;

import android.app.job.JobInfo;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("ConstantConditions")
@RunWith(AndroidJUnit4.class)
public class JobSchedulerTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private JobScheduler jobScheduler;
    private android.app.job.JobScheduler systemJobScheduler;
    private JobIdGenerator jobIdGenerator;

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        jobScheduler = androidTestObjectFactory.getJobScheduler();
        systemJobScheduler = activity.getSystemService(android.app.job.JobScheduler.class);
        jobIdGenerator = androidTestObjectFactory.getJobIdGenerator();
        jobIdGenerator.reset();
    }

    @After
    public void afterEach() {
        systemJobScheduler.cancelAll();
    }

    @Test
    public void itSchedulesAJobsWithTheCorrectParameters() {
        jobScheduler.schedule(10000, 20000, LearnificationPublishingService.class);

        JobInfo pendingJob = systemJobScheduler.getPendingJob(jobIdGenerator.last());
        assertThat(pendingJob.getMinLatencyMillis(), equalTo(10000L));
        assertThat(pendingJob.getMaxExecutionDelayMillis(), equalTo(20000L));
        assertThat(pendingJob.getService().getClassName(), equalTo(LearnificationPublishingService.class.getCanonicalName()));
    }

    @Test
    public void itCanTellIfThereIsAPendingJob() {
        jobScheduler.schedule(10000, 20000, LearnificationPublishingService.class);

        assertTrue(jobScheduler.hasPendingJob(LearnificationPublishingService.class, 15000));
    }

    @Test
    public void itCanTellIfTheIsAPendingJobIsScheduledButNotPendingBecauseItsTooFarInTheFuture() {
        jobScheduler.schedule(800000, 900000, LearnificationPublishingService.class);

        assertFalse(jobScheduler.hasPendingJob(LearnificationPublishingService.class, 15000));
    }
}
