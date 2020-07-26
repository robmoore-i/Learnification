package com.rrm.learnification.gui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.R;
import com.rrm.learnification.dailyreport.publication.DailyReportPublishingService;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.GuiTestWrapper;
import com.rrm.learnification.support.UserSimulation;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class JobDumpTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private GuiTestWrapper guiTestWrapper;
    private AndroidJobScheduler androidJobScheduler;

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        guiTestWrapper = new GuiTestWrapper(activity);
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        androidJobScheduler = androidTestObjectFactory.androidJobScheduler();
        guiTestWrapper.beforeEach();
    }

    @After
    public void afterEach() {
        guiTestWrapper.afterEach();
        androidJobScheduler.clearSchedule();
    }

    @Test
    public void showsJobRowsInTableView() {
        androidJobScheduler.schedule(10000, 20000, LearnificationPublishingService.class);
        androidJobScheduler.schedule(20000, 30000, DailyReportPublishingService.class);
        UserSimulation.waitASecond();
        UserSimulation.switchToJobDumpScreen();

        onView(withId(R.id.scheduled_jobs_table)).check(matches(hasMinimumChildCount(2)));
    }
}
