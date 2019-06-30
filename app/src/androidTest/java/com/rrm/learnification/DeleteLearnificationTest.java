package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class DeleteLearnificationTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private final TestJanitor testJanitor = new TestJanitor();

    @After
    public void afterEach() {
        testJanitor.clearApp(activityTestRule);
    }

    @Test
    public void swipingALearnificationLeftDeletesIt() {
        onView(withText(startsWith("What is the capital city of Egypt?"))).perform(swipeLeft());

        onView(withId(R.id.learnifications_list)).check(matches(withListSize(2)));
    }

    private Matcher<? super View> withListSize(final int expectedSize) {
        return new TypeSafeMatcher<View>() {

            @Override
            protected boolean matchesSafely(View item) {
                return ((RecyclerView) item).getChildCount() == expectedSize;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("View should have " + expectedSize + " items");
            }
        };
    }
}
