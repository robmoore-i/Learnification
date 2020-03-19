package com.rrm.learnification;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assume.assumeThat;

@RunWith(AndroidJUnit4.class)
public class DeleteLearningItemTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private String left;
    private String right;

    @Before
    public void beforeEach() {
        left = UUID.randomUUID().toString().substring(0, 6);
        right = UUID.randomUUID().toString().substring(0, 6);

        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText(left));
        onView(withId(R.id.right_input)).perform(typeText(right));
        onView(withId(R.id.add_learning_item_button)).perform(click());
        closeSoftKeyboard();
    }

    @After
    public void afterEach() {
        activityTestRule.getActivity().androidTestObjectFactory().getDefaultSqlLearningItemSetRecordStore().delete(new LearningItemText(left, right));
    }

    @Test
    public void swipingALearningItemLeftDeletesIt() {
        RecyclerView recyclerView = activityTestRule.getActivity().findViewById(R.id.learningitem_list);
        int initialSize = recyclerView.getChildCount();
        assumeThat(initialSize, lessThan(8));

        onView(withText(startsWith(left))).perform(swipeLeft());

        recyclerView = activityTestRule.getActivity().findViewById(R.id.learningitem_list);
        int finalSize = recyclerView.getChildCount();

        assertThat(finalSize, equalTo(initialSize - 1));
    }
}
