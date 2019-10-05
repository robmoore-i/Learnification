package com.rrm.learnification.common;

import android.app.Notification;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rrm.learnification.R;
import com.rrm.learnification.learnification.LearnificationPublishingService;
import com.rrm.learnification.main.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.rrm.learnification.common.CreatesNotificationOnStartupTest.clearNotifications;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;


@RunWith(AndroidJUnit4.class)
public class AppToolbarTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    public static Matcher<View> withToolbarTitle(final Matcher<String> textMatcher) {
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
    public void whenAppStartsUpAndNotificationIsSentTheToolbarSaysThatANotificationIsReady() throws InterruptedException {
        waitACoupleOfSeconds();

        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle(is("Learnification: sent & ready"))));
    }

    @Test
    public void ifNotificationIsCancelledThenToolbarSaysThatNoNotificationIsScheduledAfterACoupleOfSeconds() throws InterruptedException {
        clearNotifications();

        waitACoupleOfSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(is("Learnification: none scheduled")))).check(matches(isDisplayed()));
    }

    @Test
    public void ifNotificationIsCancelledButThenANewOneIsMadeThenToolbarSaysItsReady() throws InterruptedException {
        clearNotifications();

        waitACoupleOfSeconds();

        Notification learnification = activityTestRule.getActivity().getAndroidNotificationFactory().createLearnification(new LearnificationText("a", "b", "c"));
        NotificationManagerCompat.from(activityTestRule.getActivity()).notify(0, learnification);

        waitACoupleOfSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(is("Learnification: sent & ready")))).check(matches(isDisplayed()));
    }

    @Test
    public void ifNotificationIsCancelledButThenANewOneIsScheduledThenToolbarSaysItsReady() throws InterruptedException {
        clearNotifications();

        waitACoupleOfSeconds();

        activityTestRule.getActivity().getJobScheduler().schedule(10000, 20000, LearnificationPublishingService.class);

        waitACoupleOfSeconds();

        onView(allOf(withId(R.id.toolbar), withToolbarTitle(startsWith("Learnification: scheduled")))).check(matches(isDisplayed()));
    }

    private void waitACoupleOfSeconds() throws InterruptedException {
        Thread.sleep(2000);
    }
}
