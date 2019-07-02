package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class AddLearnificationTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private final TestJanitor testJanitor = new TestJanitor();

    @After
    public void afterEach() {
        testJanitor.clearApp(activityTestRule);
    }

    /*
        NOTICE OF FRAGILITY

        This test only works as long as there are 4 or fewer default learning items.
    */
    @Test
    public void typingLAndRIntoTheTextFieldsAndClickingThePlusButtonAddsALearnificationToTheList() {
        onView(withId(R.id.left_input)).perform(typeText("L"));
        onView(withId(R.id.right_input)).perform(typeText("R"));
        onView(withId(R.id.addLearnificationButton)).perform(click());

        onView(allOf(withParent(withId(R.id.learnifications_list)), withText("L - R"))).check(matches(isDisplayed()));
    }
}
