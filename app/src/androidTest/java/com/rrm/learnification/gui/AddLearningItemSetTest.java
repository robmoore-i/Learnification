package com.rrm.learnification.gui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

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
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rrm.learnification.support.CustomAssertion.assertLearningItemListHasSize;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class AddLearningItemSetTest {
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
    public void afterAddingNewLearningItemSetTheSetListHasTheNewItem() {
        String initialLearningItemSetName = activityTestRule.getActivity().<Spinner>findViewById(R.id.learning_item_set_selector).getSelectedItem().toString();

        UserSimulation.addNewLearningItemSet();
        UserSimulation.pressLearningItemSetSelector();

        onView(withText("Add new group")).check(matches(isDisplayed()));
        onView(withText(initialLearningItemSetName)).check(matches(isDisplayed()));
        onView(withText("new set 1")).check(matches(isDisplayed()));
    }

    @Test
    public void afterAddingNewLearningItemSetTheSetTitleIsUpdated() {
        UserSimulation.addNewLearningItemSet();

        onView(withId(R.id.learning_item_set_name_textbox)).check(matches(withText("new set 1")));
        onView(withId(R.id.learning_item_set_name_change_icon)).check(matches(withTagValue(equalTo("enabled"))));
    }

    @Test
    public void afterAddingNewLearningItemSetTheSetTitleCanBeChanged() {
        UserSimulation.addNewLearningItemSet("Thai");

        onView(withId(R.id.learning_item_set_selector)).check(matches(withSpinnerText("Thai")));
    }

    @Test
    public void newLearningItemSetHasNoLearningItems() {
        UserSimulation.addLearningItem("right", "left");
        UserSimulation.addNewLearningItemSet();

        assertLearningItemListHasSize(activityTestRule.getActivity(), 0);
    }

    @Test
    public void learningItemsAddedEitherSideOfARenameStayInView() {
        int expectedNumberOfDutchLearningItems = activityTestRule.getActivity().<RecyclerView>findViewById(R.id.learning_item_list).getChildCount() + 2;

        UserSimulation.addLearningItem("du", "tch");
        UserSimulation.renameLearningItemSet("Dutch");
        UserSimulation.addLearningItem("holla", "ndish");

        onView(allOf(withParent(withId(R.id.learning_item_list)), withText("du - tch"))).check(matches(isDisplayed()));
        onView(allOf(withParent(withId(R.id.learning_item_list)), withText("holla - ndish"))).check(matches(isDisplayed()));
        assertLearningItemListHasSize(activityTestRule.getActivity(), expectedNumberOfDutchLearningItems);
    }

    @Test
    public void canAddLearningItemsInANewSetAfterARename() {
        UserSimulation.addLearningItem("du", "tch");
        UserSimulation.renameLearningItemSet("Dutch");
        UserSimulation.addLearningItem("holla", "ndish");

        UserSimulation.addNewLearningItemSet("Navajo");
        UserSimulation.addLearningItem("nav", "ajo");

        onView(allOf(withParent(withId(R.id.learning_item_list)), withText("nav - ajo"))).check(matches(isDisplayed()));
        assertLearningItemListHasSize(activityTestRule.getActivity(), 1);
    }

    @Test
    public void renamingASetThenMakingANewOneThenSwitchingBackToTheOldOneResultsInTheOldOnesItemsBeingPreserved() {
        int expectedNumberOfDutchLearningItems = activityTestRule.getActivity().<RecyclerView>findViewById(R.id.learning_item_list).getChildCount() + 1;
        UserSimulation.addLearningItem("du", "tch");
        UserSimulation.renameLearningItemSet("Dutch");
        UserSimulation.addNewLearningItemSet("Navajo");
        UserSimulation.addLearningItem("nav", "ajo");

        UserSimulation.switchToLearningItemSet("Dutch");

        onView(allOf(withParent(withId(R.id.learning_item_list)), withText("du - tch"))).check(matches(isDisplayed()));
        assertLearningItemListHasSize(activityTestRule.getActivity(), expectedNumberOfDutchLearningItems);
    }

    @Test
    public void whenAddingANewLearningItemSetThenSwitchingToAnExistingOneTheTitleStaysUpToDate() {
        String initialLearningItemSetName = activityTestRule.getActivity().<Spinner>findViewById(R.id.learning_item_set_selector).getSelectedItem().toString();

        UserSimulation.addNewLearningItemSet();
        UserSimulation.switchToLearningItemSet(initialLearningItemSetName);

        onView(withId(R.id.learning_item_set_name_textbox)).check(matches(withText(initialLearningItemSetName)));
        onView(withId(R.id.learning_item_set_name_change_icon)).check(matches(withTagValue(equalTo("disabled"))));
    }
}
