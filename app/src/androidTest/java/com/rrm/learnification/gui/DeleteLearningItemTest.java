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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assume.assumeThat;

@RunWith(AndroidJUnit4.class)
public class DeleteLearningItemTest {
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
    public void swipingALearningItemLeftDeletesIt() {
        UserSimulation.addLearningItem(left, right);
        int initialNumberOfLearningItems = UserSimulation.countLearningItems(activityTestRule.getActivity());
        assumeThat(initialNumberOfLearningItems, lessThan(8));

        UserSimulation.swipeLearningItem(left, right);

        int newNumberOfLearningItems = UserSimulation.countLearningItems(activityTestRule.getActivity());
        assertThat(newNumberOfLearningItems, equalTo(initialNumberOfLearningItems - 1));
    }
}
