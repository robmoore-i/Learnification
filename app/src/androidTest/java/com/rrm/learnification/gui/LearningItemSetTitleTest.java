package com.rrm.learnification.gui;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.GuiTestWrapper;
import com.rrm.learnification.support.UserSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static com.rrm.learnification.support.CustomAssertion.assertLearningItemListHasSize;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
public class LearningItemSetTitleTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private GuiTestWrapper guiTestWrapper;

    @Before
    public void beforeEach() {
        guiTestWrapper = new GuiTestWrapper(activityTestRule.getActivity());
        guiTestWrapper.beforeEach();
    }

    @After
    public void afterEach() {
        guiTestWrapper.afterEach();
    }

    @Test
    public void theTitleIsDisplayed() {
        onView(ViewMatchers.withId(R.id.learning_item_set_name_textbox)).check(matches(isDisplayed()));
    }

    @Test
    public void whenSetIsRenamedTheLearningItemListDisplaysTheSameItems() {
        int initialNumberOfLearningItems = activityTestRule.getActivity().<RecyclerView>findViewById(R.id.learning_item_list).getChildCount();

        UserSimulation.addLearningItem("left", "right");
        UserSimulation.renameLearningItemSet("Chinese");

        assertLearningItemListHasSize(activityTestRule.getActivity(), initialNumberOfLearningItems + 1);
    }


    @Test
    public void whenTheCurrentLearningItemIsRenamedFromDefaultValueTheSelectorShowsTheUpdatedName() {
        UserSimulation.renameLearningItemSet("default");

        UserSimulation.renameLearningItemSet("Thai");

        onView(withId(R.id.learning_item_set_selector)).check(matches(withSpinnerText(containsString("Thai"))));
    }

    @Test
    public void whenTheCurrentLearningItemIsRenamedFromNonDefaultValueTheSelectorShowsTheUpdatedName() {
        UserSimulation.renameLearningItemSet("Chinese");

        UserSimulation.renameLearningItemSet("Thai");

        onView(withId(R.id.learning_item_set_selector)).check(matches(withSpinnerText(containsString("Thai"))));
    }
}
