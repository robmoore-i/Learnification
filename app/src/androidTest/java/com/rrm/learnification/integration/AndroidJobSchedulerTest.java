package com.rrm.learnification.integration;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AndroidJobSchedulerTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private AndroidJobScheduler androidJobScheduler;

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        androidJobScheduler = androidTestObjectFactory.androidJobScheduler();
    }

    @After
    public void tearDown() {
        androidJobScheduler.clearSchedule();
    }

    @Test
    public void returnsEmptyIfAskedForMsUntilNextJobWhereNoJobWasScheduled() {
        androidJobScheduler.schedule(10000, 20000, LearnificationPublishingService.class);

        assertFalse(androidJobScheduler.msUntilNextJob(Object.class).isPresent());
    }

    @Test
    public void returnsMsUntilNextJobForScheduledJob() throws InterruptedException {
        androidJobScheduler.schedule(10000, 20000, LearnificationPublishingService.class);
        // The Oppo phone I'm now testing on is quite slow with job scheduling. Give it some time.
        Thread.sleep(5000);

        assertTrue(androidJobScheduler.msUntilNextJob(LearnificationPublishingService.class).isPresent());
    }
}
