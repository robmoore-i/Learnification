package com.rrm.learnification.gui;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.NotificationManagerCompat;

import com.rrm.learnification.R;
import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.learnification.creation.LearnificationNotificationFactory;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rrm.learnification.support.CustomMatcher.withToolbarTitle;
import static com.rrm.learnification.support.LearnificationAppAssumption.assumeThatThereAreAnyLearningItems;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;


@RunWith(AndroidJUnit4.class)
public class AppToolbarTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private GuiTestWrapper guiTestWrapper;
    private android.app.job.JobScheduler androidJobScheduler;
    private LearnificationNotificationFactory notificationFactory;
    private NotificationManagerCompat notificationManagerCompat;
    private JobScheduler learnificationJobScheduler;

    @Before
    public void beforeEach() {
        guiTestWrapper = new GuiTestWrapper(activityTestRule.getActivity());
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        new NotificationIdGenerator(new AndroidLogger(), androidTestObjectFactory.getFileStorageAdaptor()).reset();
        androidJobScheduler = activityTestRule.getActivity().getSystemService(android.app.job.JobScheduler.class);
        notificationFactory = androidTestObjectFactory.getLearnificationNotificationFactory();
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
    public void ifNotificationIsCancelledThenToolbarSaysThatNoNotificationIsScheduledAfterACoupleOfSeconds() throws InterruptedException {
        UserSimulation.clearNotifications();
        androidJobScheduler.cancelAll();
        UserSimulation.waitSomeSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(is("Learnification")))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.toolbar_button), withText(">>"))).check(matches(isDisplayed()));
    }

    @Test
    public void ifNotificationIsCancelledButThenANewOneIsMadeThenToolbarSaysItsReady() throws InterruptedException {
        UserSimulation.clearNotifications();
        UserSimulation.waitACoupleOfSeconds();

        IdentifiedNotification learnification = notificationFactory.createLearnification(new LearnificationText("a", "b"));
        notificationManagerCompat.notify(learnification.id(), learnification.notification());
        UserSimulation.waitACoupleOfSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(is("Learnification ready")))).check(matches(isDisplayed()));
    }

    @Test
    public void ifNotificationIsCancelledButThenANewOneIsScheduledThenToolbarSaysItsReady() throws InterruptedException {
        UserSimulation.clearNotifications();
        UserSimulation.waitACoupleOfSeconds();

        learnificationJobScheduler.schedule(10000, 20000, LearnificationPublishingService.class);
        UserSimulation.waitACoupleOfSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(startsWith("Learnification in ")))).check(matches(isDisplayed()));
    }

    @Test
    public void whenAppStartsUpAndNotificationIsSentTheToolbarSaysThatANotificationIsReady() throws InterruptedException {
        assumeThatThereAreAnyLearningItems(activityTestRule);
        UserSimulation.waitACoupleOfSeconds();

        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle(is("Learnification ready"))));
    }
}
