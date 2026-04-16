package com.rrm.learnification.gui;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.UserSimulation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    @Test
    public void settingTheLearnificationPromptSavesItAndIsStillSetIfYouGoInAndOutOfTheSettingsScreen() {
        UserSimulation.switchToSettingsScreen();
        UserSimulation.selectPromptStrategyOption("rightToLeft");
        UserSimulation.saveSettingsAndReturnToSetEditor();
        UserSimulation.switchToSettingsScreen();

        onView(withId(R.id.right_to_left)).check(matches(isChecked()));
    }
}
