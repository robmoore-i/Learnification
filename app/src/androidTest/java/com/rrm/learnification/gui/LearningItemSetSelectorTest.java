package com.rrm.learnification.gui;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.DatabaseTestWrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

public class LearningItemSetSelectorTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private DatabaseTestWrapper databaseTestWrapper;
    private String updatedLearningItemSetName;

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        databaseTestWrapper = new DatabaseTestWrapper(activity);
        databaseTestWrapper.beforeEach();
        updatedLearningItemSetName = "Thai";
    }

    @After
    public void afterEach() {
        databaseTestWrapper.afterEach();
    }

    @Test
    public void whenTheCurrentLearningItemIsRenamedFromDefaultValueTheSelectorShowsTheUpdatedName() {
        String oldLearningItemSetName = "default";
        // Set up old name
        onView(ViewMatchers.withId(R.id.learning_item_set_name_change_icon)).perform(click());
        onView(withId(R.id.learning_item_set_name_textbox)).perform(clearText(), typeText(oldLearningItemSetName));
        onView(withId(R.id.learning_item_set_name_change_icon)).perform(click());

        // Update to new name
        onView(ViewMatchers.withId(R.id.learning_item_set_name_change_icon)).perform(click());
        onView(withId(R.id.learning_item_set_name_textbox)).perform(clearText(), typeText(updatedLearningItemSetName));
        onView(withId(R.id.learning_item_set_name_change_icon)).perform(click());

        int spinnerId = R.id.learning_item_set_selector;
        onView(withId(spinnerId)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(updatedLearningItemSetName))).perform(click());
        onView(withId(spinnerId)).check(matches(withSpinnerText(containsString(updatedLearningItemSetName))));
    }

    @Test
    public void whenTheCurrentLearningItemIsRenamedFromNonDefaultValueTheSelectorShowsTheUpdatedName() {
        String oldLearningItemSetName = "Chinese";
        // Set up old name
        onView(ViewMatchers.withId(R.id.learning_item_set_name_change_icon)).perform(click());
        onView(withId(R.id.learning_item_set_name_textbox)).perform(clearText(), typeText(oldLearningItemSetName));
        onView(withId(R.id.learning_item_set_name_change_icon)).perform(click());

        // Update to new name
        onView(ViewMatchers.withId(R.id.learning_item_set_name_change_icon)).perform(click());
        onView(withId(R.id.learning_item_set_name_textbox)).perform(clearText(), typeText(updatedLearningItemSetName));
        onView(withId(R.id.learning_item_set_name_change_icon)).perform(click());

        int spinnerId = R.id.learning_item_set_selector;
        onView(withId(spinnerId)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(updatedLearningItemSetName))).perform(click());
        onView(withId(spinnerId)).check(matches(withSpinnerText(containsString(updatedLearningItemSetName))));
    }
}
