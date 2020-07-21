package com.rrm.learnification.integration;

import android.app.Notification;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.dailyreport.creation.DailyReportFactory;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.notification.NotificationType;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DailyReportFactoryTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private DailyReportFactory dailyReportFactory;

    @Before
    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        dailyReportFactory = androidTestObjectFactory.getDailyReportFactory();
    }

    @Test
    public void itGeneratesDailyReportWithABundleContainingTheNotificationType() {
        Notification learnification = dailyReportFactory.dailyReport().notification();

        assertThat(learnification.extras.getString(NotificationType.NOTIFICATION_TYPE_EXTRA_NAME), equalTo(NotificationType.DAILY_REPORT));
    }
}
