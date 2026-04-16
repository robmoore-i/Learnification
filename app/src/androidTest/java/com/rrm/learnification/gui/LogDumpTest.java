package com.rrm.learnification.gui;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.GuiTestWrapper;
import com.rrm.learnification.support.UserSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LogDumpTest {
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
    public void showsLogLinesInListView() {
        UserSimulation.switchToLogDumpScreen();

        onView(withId(R.id.log_line_list)).check(matches(hasMinimumChildCount(4)));
    }
}
