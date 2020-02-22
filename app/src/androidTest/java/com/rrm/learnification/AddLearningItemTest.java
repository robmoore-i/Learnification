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

import java.util.List;
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

    private String left;
    private String right;

    @Before
    public void beforeEach() {
        left = UUID.randomUUID().toString().substring(0, 6);
        right = UUID.randomUUID().toString().substring(0, 6);
    }

    @After
    public void afterEach() {
        ItemRepository<LearningItem> learningItemRepository = activityTestRule.getActivity().androidTestObjectFactory().getLearningItemRepository();
        List<LearningItem> learningItems = learningItemRepository.items();
        for (int i = 0; i < learningItems.size(); i++) {
            LearningItem learningItem = learningItems.get(i);
            if (learningItem.left.equals(left) && learningItem.right.equals(right)) {
                learningItemRepository.removeAt(i);
                return;
            }
        }
    }

    @Test
    public void typingLAndRIntoTheTextFieldsAndClickingThePlusButtonAddsALearnificationToTheList() {
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText(left));
        onView(withId(R.id.right_input)).perform(typeText(right));
        onView(withId(R.id.add_learning_item_button)).perform(click());
        closeSoftKeyboard();

        onView(allOf(withParent(withId(R.id.learningitem_list)), withText(left + " - " + right))).check(matches(isDisplayed()));
    }
}
