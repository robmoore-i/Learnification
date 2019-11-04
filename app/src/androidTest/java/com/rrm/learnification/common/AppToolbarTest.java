package com.rrm.learnification.common;

import android.app.Notification;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import com.rrm.learnification.R;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.publication.LearnificationPublishingService;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assume.assumeFalse;


@RunWith(AndroidJUnit4.class)
public class AppToolbarTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> withToolbarTitle(final Matcher<String> textMatcher) {
        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    @Test
    public void theFastForwardButtonIsDisplayed() {
        onView(allOf(withId(R.id.toolbar_button), withText(">>"))).check(matches(isDisplayed()));
    }

    @Test
    public void ifNotificationIsCancelledThenToolbarSaysThatNoNotificationIsScheduledAfterACoupleOfSeconds() throws InterruptedException {
        clearNotifications();
        clearJobs();

        waitACoupleOfSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(is("Learnification")))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.toolbar_button), withText(">>"))).check(matches(isDisplayed()));
    }

    @Test
    public void ifNotificationIsCancelledButThenANewOneIsMadeThenToolbarSaysItsReady() throws InterruptedException {
        clearNotifications();

        waitACoupleOfSeconds();

        Notification learnification = activityTestRule.getActivity().getAndroidNotificationFactory().createLearnification(new LearnificationText("a", "b", "c"));
        NotificationManagerCompat.from(activityTestRule.getActivity()).notify(0, learnification);

        waitACoupleOfSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(is("Learnification ready")))).check(matches(isDisplayed()));
    }

    @Test
    public void ifNotificationIsCancelledButThenANewOneIsScheduledThenToolbarSaysItsReady() throws InterruptedException {
        clearNotifications();

        waitACoupleOfSeconds();

        activityTestRule.getActivity().getJobScheduler().schedule(10000, 20000, LearnificationPublishingService.class);

        waitACoupleOfSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(startsWith("Learnification in ")))).check(matches(isDisplayed()));
    }

    @Test
    public void whenAppStartsUpAndNotificationIsSentTheToolbarSaysThatANotificationIsReady() throws InterruptedException {
        // If there are no learning items, the app will choose not to generate a learnification.
        assumeFalse(activityTestRule.getActivity().getLearningItemRepository().items().isEmpty());

        waitACoupleOfSeconds();

        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle(is("Learnification ready"))));
    }

    private static void clearNotifications() {
        int notificationTimeoutMs = 1000;
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        try {
            device.wait(Until.hasObject(By.textStartsWith("com.rrm.learnification")), notificationTimeoutMs);
            device.findObject(By.text("CLEAR ALL")).click();
        } catch (Exception e) {
            try {
                device.findObject(By.text("CLEAR ALL")).click();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            device.pressBack();
        }
    }

    private void clearJobs() {
        activityTestRule.getActivity().getSystemService(android.app.job.JobScheduler.class).cancelAll();
    }

    private void waitACoupleOfSeconds() throws InterruptedException {
        Thread.sleep(2000);
    }
}
