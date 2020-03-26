package com.rrm.learnification.gui;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SetLearnificationPromptStrategyTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    @Test
    public void settingTheLearnificationPromptSavesItAndIsStillSetIfYouGoInAndOutOfTheSettingsScreen() {
        goToSettings();
        onView(ViewMatchers.withId(R.id.right_to_left)).perform(click());
        onView(withId(R.id.save_settings_button)).perform(click());
        goToSettings();
        onView(withId(R.id.right_to_left)).check(matches(isChecked()));
    }

    public void goToSettings() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.action_settings)).perform(click());
    }
}