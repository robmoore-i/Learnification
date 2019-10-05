package com.rrm.learnification.common;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.rrm.learnification.R;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearningItemStorage;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DeleteLearningItemTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private LearningItemStorage learningItemStorage;
    private LearningItem removedLearningItem;

    @Before
    public void beforeEach() {
        learningItemStorage = activityTestRule.getActivity().getLearningItemStorage();
        removedLearningItem = learningItemStorage.read().get(0);
    }

    @After
    public void afterEach() {
        learningItemStorage.write(removedLearningItem);
    }

    @Test
    public void swipingALearnificationLeftDeletesIt() {
        RecyclerView recyclerView = activityTestRule.getActivity().findViewById(R.id.learnifications_list);
        int initialSize = recyclerView.getChildCount();

        onView(withText(startsWith(removedLearningItem.left))).perform(swipeLeft());

        recyclerView = activityTestRule.getActivity().findViewById(R.id.learnifications_list);
        int finalSize = recyclerView.getChildCount();

        assertThat(finalSize, equalTo(initialSize - 1));
    }
}
