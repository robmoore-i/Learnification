package com.rrm.learnification;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.storage.ItemRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class UpdateLearningItemTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private String left;
    private String initialRight;
    private String updatedRight;
    private String extraText = "_foo";

    @Before
    public void beforeEach() {
        left = UUID.randomUUID().toString().substring(0, 6);
        initialRight = UUID.randomUUID().toString().substring(0, 6);
        updatedRight = initialRight + extraText;
    }

    @After
    public void afterEach() {
        ItemRepository<LearningItem> learningItemRepository = activityTestRule.getActivity().androidTestObjectFactory().getLearningItemRepository();
        learningItemRepository.remove(new LearningItem(left, initialRight));
        learningItemRepository.remove(new LearningItem(left, updatedRight));
    }

    @Test
    public void editingALearningItemAndPressingUpdateCausesItToStayWhenUnfocused() {
        // Add the learning item
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText(left));
        onView(withId(R.id.right_input)).perform(typeText(initialRight));
        onView(withId(R.id.add_learning_item_button)).perform(click());
        closeSoftKeyboard();

        // Update the learning item
        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + initialRight))).perform(typeText(extraText));
        closeSoftKeyboard();

        // Click the button
        onView(withId(R.id.update_learning_item_button)).perform(click());

        // When you change focus, check that the text stays there
        onView(ViewMatchers.withId(R.id.left_input)).perform(click());
        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + updatedRight))).check(matches(isDisplayed()));
    }

    @Test
    public void editingALearningItemUnfocusingCausesTheValueToRevert() {
        // Add the learning item
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText(left));
        onView(withId(R.id.right_input)).perform(typeText(initialRight));
        onView(withId(R.id.add_learning_item_button)).perform(click());
        closeSoftKeyboard();

        // Update the learning item
        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + initialRight))).perform(typeText(extraText));
        closeSoftKeyboard();

        // When you change focus, check that the text is reverted
        onView(ViewMatchers.withId(R.id.left_input)).perform(click());
        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + initialRight))).check(matches(isDisplayed()));
        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + updatedRight))).check(doesNotExist());
    }

    @Test
    public void deletingALearningItemMidEditCausesTheUpdateButtonToBecomeDisabled() {
        // Add the learning item
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText(left));
        onView(withId(R.id.right_input)).perform(typeText(initialRight));
        onView(withId(R.id.add_learning_item_button)).perform(click());
        closeSoftKeyboard();

        // Update the learning item
        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + initialRight))).perform(typeText(extraText));
        closeSoftKeyboard();

        // Delete the learning item
        onView(withText(startsWith(left))).perform(swipeLeft());

        // When you click the button, the app doesn't break, and the item is gone
        onView(withId(R.id.update_learning_item_button)).perform(click());
        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + initialRight))).check(doesNotExist());
        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + updatedRight))).check(doesNotExist());
    }
}
