package com.rrm.learnification.gui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.support.UserSimulation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
