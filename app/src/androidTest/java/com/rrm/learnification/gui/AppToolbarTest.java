package com.rrm.learnification.gui;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.R;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.learnification.creation.LearnificationFactory;
import com.rrm.learnification.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.notification.IdentifiedNotification;
import com.rrm.learnification.notification.NotificationIdGenerator;
import com.rrm.learnification.support.GuiTestWrapper;
import com.rrm.learnification.support.UserSimulation;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;


@RunWith(AndroidJUnit4.class)
public class AppToolbarTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private GuiTestWrapper guiTestWrapper;
    private android.app.job.JobScheduler androidJobScheduler;
    private LearnificationFactory learnificationFactory;
    private NotificationManagerCompat notificationManagerCompat;
    private JobScheduler learnificationJobScheduler;

    @Before
    public void beforeEach() {
        guiTestWrapper = new GuiTestWrapper(activityTestRule.getActivity());
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        new NotificationIdGenerator(new AndroidLogger(), androidTestObjectFactory.getLearnificationAppDatabase()).reset();
        androidJobScheduler = activityTestRule.getActivity().getSystemService(android.app.job.JobScheduler.class);
        learnificationFactory = androidTestObjectFactory.getLearnificationFactory();
        notificationManagerCompat = NotificationManagerCompat.from(activityTestRule.getActivity());
        learnificationJobScheduler = androidTestObjectFactory.getJobScheduler();
        guiTestWrapper.beforeEach();
    }

    @After
    public void afterEach() {
        guiTestWrapper.afterEach();
    }

    @Test
    public void theFastForwardButtonIsDisplayed() {
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.toolbar_button), withText(">>"))).check(matches(isDisplayed()));
    }

    @Test
    public void ifNotificationIsCancelledThenToolbarSaysThatNoNotificationIsScheduledAfterACoupleOfSeconds() {
        UserSimulation.clearNotifications(activityTestRule.getActivity());
        androidJobScheduler.cancelAll();
        UserSimulation.waitSomeSeconds();

        UserSimulation.checkToolbarTitle(is("Learnification"));
        UserSimulation.checkForFastForwardButton();
    }

    @Test
    public void ifNotificationIsCancelledButThenANewOneIsMadeThenToolbarSaysItsReady() {
        UserSimulation.clearNotifications(activityTestRule.getActivity());
        UserSimulation.waitACoupleOfSeconds();

        IdentifiedNotification learnification = learnificationFactory.learnification();
        notificationManagerCompat.notify(learnification.id(), learnification.notification());
        UserSimulation.waitACoupleOfSeconds();

        UserSimulation.checkToolbarTitle(is("Learnification ready"));
    }

    @Test
    public void ifNotificationIsCancelledButThenANewOneIsScheduledThenToolbarSaysWhenItsArriving() {
        UserSimulation.clearNotifications(activityTestRule.getActivity());
        UserSimulation.waitACoupleOfSeconds();

        learnificationJobScheduler.schedule(10000, 20000, LearnificationPublishingService.class);
        UserSimulation.waitACoupleOfSeconds();

        UserSimulation.checkToolbarTitle(startsWith("Learnification in "));
    }
}
