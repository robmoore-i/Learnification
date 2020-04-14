package com.rrm.learnification.gui;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.DatabaseTestWrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class AddLearningItemSetTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private DatabaseTestWrapper databaseTestWrapper;

    @Before
    public void beforeEach() {
        databaseTestWrapper = new DatabaseTestWrapper(activityTestRule.getActivity());
        databaseTestWrapper.beforeEach();
    }

    @After
    public void afterEach() {
        databaseTestWrapper.afterEach();
    }

    @Test
    public void afterAddingNewLearningItemSetTheSetListHasTheNewItem() {
        onView(withId(R.id.learning_item_set_selector)).perform(click());
        onView(withText("Add new group")).perform(click());

        onView(withId(R.id.learning_item_set_selector)).perform(click());
        onView(withText("Add new group")).check(matches(isDisplayed()));
        onView(withText("default")).check(matches(isDisplayed()));
        onView(withText("new set 1")).check(matches(isDisplayed()));
    }

    @Test
    public void afterAddingNewLearningItemSetTheSetTitleIsUpdated() {
        onView(withId(R.id.learning_item_set_selector)).perform(click());
        onView(withText("Add new group")).perform(click());

        onView(withId(R.id.learning_item_set_name_textbox)).check(matches(withText("new set 1")));
    }

    @Test
    public void afterAddingNewLearningItemSetTheSetTitleCanBeChanged() {
        onView(withId(R.id.learning_item_set_selector)).perform(click());
        onView(withText("Add new group")).perform(click());
        onView(withId(R.id.learning_item_set_name_textbox)).perform(clearText(), typeText("Thai"));
        onView(withId(R.id.learning_item_set_name_change_icon)).perform(click());

        onView(withId(R.id.learning_item_set_selector)).check(matches(withSpinnerText("Thai")));
    }

    @Test
    public void newLearningItemSetHasNoLearningItems() {
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText("left"));
        onView(withId(R.id.right_input)).perform(typeText("right"));
        onView(withId(R.id.add_learning_item_button)).perform(click());
        onView(withId(R.id.learning_item_set_selector)).perform(click());
        onView(withText("Add new group")).perform(click());

        assertThat(activityTestRule.getActivity()
                        .<RecyclerView>findViewById(R.id.learning_item_list)
                        .getChildCount(),
                equalTo(0));
    }
}
