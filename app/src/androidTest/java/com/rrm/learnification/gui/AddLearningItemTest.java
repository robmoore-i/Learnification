package com.rrm.learnification.gui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.GuiTestWrapper;
import com.rrm.learnification.support.UserSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

@RunWith(AndroidJUnit4.class)
public class AddLearningItemTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private GuiTestWrapper guiTestWrapper;

    private String left = UUID.randomUUID().toString().substring(0, 6);
    private String right = UUID.randomUUID().toString().substring(0, 6);

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
    public void typingLAndRIntoTheTextFieldsAndClickingThePlusButtonAddsALearnificationToTheList() {
        UserSimulation.addLearningItem(left, right);

        UserSimulation.checkForLearningItem(left, right);
    }
}
