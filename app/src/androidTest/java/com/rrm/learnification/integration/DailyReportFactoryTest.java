package com.rrm.learnification.integration;

import android.app.Notification;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.dailyreport.creation.DailyReportFactory;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.AndroidNotificationPublisher;
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
    private AndroidNotificationPublisher androidNotificationPublisher;

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        dailyReportFactory = androidTestObjectFactory.getDailyReportFactory();
        androidNotificationPublisher = new AndroidNotificationPublisher(new AndroidLogger(), NotificationManagerCompat.from(activity));
    }

    @Test
    public void itGeneratesDailyReportWithABundleContainingTheNotificationType() {
        Notification learnification = dailyReportFactory.dailyReport().notification();

        assertThat(learnification.extras.getString(NotificationType.NOTIFICATION_TYPE_EXTRA_NAME), equalTo(NotificationType.DAILY_REPORT));
    }

    @Test
    public void canPublishDailyReport() {
        androidNotificationPublisher.publish(dailyReportFactory.dailyReport());
    }
}
