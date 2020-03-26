package com.rrm.learnification.ui;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.R;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.support.DatabaseTestWrapper;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.UUID;

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
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private DatabaseTestWrapper databaseTestWrapper;
    private LearningItemSqlTableClient learningItemSqlTableClient;

    private String left;
    private String right;

    @Before
    public void beforeEach() {
        databaseTestWrapper = new DatabaseTestWrapper(activityTestRule.getActivity());
        databaseTestWrapper.beforeEach();
        left = UUID.randomUUID().toString().substring(0, 6);
        right = UUID.randomUUID().toString().substring(0, 6);
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        learningItemSqlTableClient = androidTestObjectFactory.getLearningItemSqlTableClient();
    }

    @After
    public void afterEach() {
        learningItemSqlTableClient.deleteAll(Collections.singletonList(new LearningItem(left, right, "default")));
        databaseTestWrapper.afterEach();
    }

    @Test
    public void typingLAndRIntoTheTextFieldsAndClickingThePlusButtonAddsALearnificationToTheList() {
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText(left));
        onView(withId(R.id.right_input)).perform(typeText(right));
        onView(withId(R.id.add_learning_item_button)).perform(click());
        closeSoftKeyboard();

        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(left + " - " + right))).check(matches(isDisplayed()));
    }
}
