package com.rrm.learnification.common;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.R;
import com.rrm.learnification.main.MainActivity;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
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
public class AddLearningItemTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private final TestJanitor testJanitor = new TestJanitor();

    @After
    public void afterEach() {
        testJanitor.clearApp(activityTestRule);
    }

    @Test
    public void typingLAndRIntoTheTextFieldsAndClickingThePlusButtonAddsALearnificationToTheList() {
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText("L"));
        onView(withId(R.id.right_input)).perform(typeText("R"));
        onView(withId(R.id.add_learning_item_button)).perform(click());
        closeSoftKeyboard();

        onView(allOf(withParent(withId(R.id.learnifications_list)), withText("L - R"))).check(matches(isDisplayed()));
    }
}
