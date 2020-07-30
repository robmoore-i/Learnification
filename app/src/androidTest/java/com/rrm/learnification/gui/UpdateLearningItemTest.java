package com.rrm.learnification.gui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.GuiTestWrapper;
import com.rrm.learnification.support.UserSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rrm.learnification.button.ButtonColour.GRAYED_OUT;
import static com.rrm.learnification.button.ButtonColour.READY_TO_BE_CLICKED;
import static com.rrm.learnification.support.CustomAssertion.assertButtonHasColour;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class UpdateLearningItemTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private GuiTestWrapper guiTestWrapper;

    private String extraText = "_foo";
    private String left = UUID.randomUUID().toString().substring(0, 6);
    private String initialRight = UUID.randomUUID().toString().substring(0, 6);
    private String updatedRight = initialRight + extraText;

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
    public void updateLearningItemButtonChangesColourWhenReadyToBeClicked() {
        UserSimulation.addLearningItem(left, initialRight);

        UserSimulation.typeOutLearningItemListEntryUpdate(left, initialRight, extraText);

        assertButtonHasColour(activityTestRule.getActivity(), R.id.update_learning_item_button, READY_TO_BE_CLICKED.intValue());
    }

    @Test
    public void updateLearningItemButtonBecomesDisabledAfterBeingClicked() {
        UserSimulation.addLearningItem(left, initialRight);

        UserSimulation.updateLearningItem(left, initialRight, extraText);

        assertButtonHasColour(activityTestRule.getActivity(), R.id.update_learning_item_button, GRAYED_OUT.intValue());
    }

    @Test
    public void editingALearningItemAndPressingUpdateCausesItToStayWhenUnfocused() {
        UserSimulation.addLearningItem(left, initialRight);

        UserSimulation.updateLearningItem(left, initialRight, extraText);
        UserSimulation.focusLeftInputForNewLearningItem();

        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(left + " - " + updatedRight))).check(matches(isDisplayed()));
        assertButtonHasColour(activityTestRule.getActivity(), R.id.update_learning_item_button, GRAYED_OUT.intValue());
    }

    @Test
    public void editingALearningItemUnfocusingCausesTheValueToRevert() {
        UserSimulation.addLearningItem(left, initialRight);

        UserSimulation.typeOutLearningItemListEntryUpdate(left, initialRight, extraText);
        UserSimulation.focusLeftInputForNewLearningItem();

        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(left + " - " + initialRight))).check(matches(isDisplayed()));
        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(left + " - " + updatedRight))).check(doesNotExist());
    }

    @Test
    public void deletingALearningItemMidEditCausesTheUpdateButtonToGreyOut() {
        UserSimulation.addLearningItem(left, initialRight);

        UserSimulation.typeOutLearningItemListEntryUpdate(left, initialRight, extraText);
        UserSimulation.deleteLearningItem(left, updatedRight);

        assertButtonHasColour(activityTestRule.getActivity(), R.id.update_learning_item_button, GRAYED_OUT.intValue());
    }

    @Test
    public void deletingALearningItemMidEditCausesTheLearningItemToBeGone() {
        UserSimulation.addLearningItem(left, initialRight);

        UserSimulation.typeOutLearningItemListEntryUpdate(left, initialRight, extraText);
        UserSimulation.deleteLearningItem(left, updatedRight);
        UserSimulation.pressUpdateLearningItemButton();

        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(left + " - " + initialRight))).check(doesNotExist());
        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(left + " - " + updatedRight))).check(doesNotExist());
    }
}
