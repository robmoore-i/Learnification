package com.rrm.learnification.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.widget.NumberPicker;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import com.rrm.learnification.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class contains Espresso controls for all the actions that I expect a user to take in the
 * app. Of course, this is a very limited set of what they are capable of, however, it can grow
 * as those assumptions become invalidated
 */
public class UserSimulation {

    // ANDROID

    public static void pressBack() {
        Espresso.pressBack();
    }

    public static void pressHome() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();
    }

    public static void openApp() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.rrm.learnification");
        assertThat(intent, notNullValue());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void clearNotifications() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        try {
            device.wait(Until.hasObject(By.pkg("com.rrm.learnification")), 1000);
            device.findObject(By.text("CLEAR ALL")).click();
        } catch (Exception e) {
            try {
                device.findObject(By.text("CLEAR ALL")).click();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            device.pressBack();
        }
    }

    // ADD LEARNING ITEM

    public static void typeOutNewLearningItemIntoTextFields(String left, String right) {
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText(left));
        onView(withId(R.id.right_input)).perform(typeText(right));
    }

    public static void pressAddLearningItemButton() {
        onView(withId(R.id.add_learning_item_button)).perform(click());
        closeSoftKeyboard();
    }

    public static void addLearningItem(String left, String right) {
        typeOutNewLearningItemIntoTextFields(left, right);
        pressAddLearningItemButton();
    }

    // UPDATE LEARNING ITEM

    public static void typeOutLearningItemListEntryUpdate(String oldLeft, String oldRight, String newLeft, String newRight) {
        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(oldLeft + " - " + oldRight)))
                .perform(clearText(), typeText(newLeft + " - " + newRight));
        closeSoftKeyboard();
    }

    public static void pressUpdateLearningItemButton() {
        onView(withId(R.id.update_learning_item_button)).perform(click());
    }

    public static void updateLearningItem(String oldLeft, String oldRight, String newLeft, String newRight) {
        typeOutLearningItemListEntryUpdate(oldLeft, oldRight, newLeft, newRight);
        pressUpdateLearningItemButton();
    }

    // DELETE LEARNING ITEM

    public static void swipeLearningItem(String left, String right) {
        onView(withText(equalTo(left + " - " + right))).perform(swipeLeft());
    }

    public static void deleteLearningItem(String left, String right) {
        swipeLearningItem(left, right);
    }

    // SETTINGS

    public static void pressOptionMenu() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
    }

    public static void pressSettingsOptionOnOptionMenu() {
        onView(withText(R.string.action_settings)).perform(click());
    }

    public static void switchToSettingsScreen() {
        pressOptionMenu();
        pressSettingsOptionOnOptionMenu();
    }

    public static void spinLearnificationDelaySpinnerToValue(Activity activity, int newLearnificationDelayValueMinutes) {
        NumberPicker picker = activity.findViewById(R.id.learnification_delay_input);
        picker.setValue(newLearnificationDelayValueMinutes);
    }

    public static void selectPromptStrategyOption(String rightToLeft_leftToRight_or_mixed) {
        if ("rightToLeft".equals(rightToLeft_leftToRight_or_mixed)) {
            onView(withId(R.id.right_to_left)).check(matches(isChecked()));
        } else if ("leftToRight".equals(rightToLeft_leftToRight_or_mixed)) {
            onView(withId(R.id.left_to_right)).check(matches(isChecked()));
        } else if ("mixed".equals(rightToLeft_leftToRight_or_mixed)) {
            onView(withId(R.id.mixed_left_and_right)).check(matches(isChecked()));
        } else {
            throw new IllegalArgumentException("Expected one of 'rightToLeft', 'leftToRight' or 'mixed'");
        }
    }

    public static void pressSaveSettingsButton() {
        onView(withId(R.id.save_settings_button)).perform(click());
    }

    // TOOLBAR

    public static void pressLearnificationFastForwardButton() {
        onView(withId(R.id.toolbar_button)).perform(click());
    }

    // REFRESH

    public static void pressRefreshOptionOnOptionMenu() {
        onView(withText(R.string.refresh_learning_item_list)).perform(click());
    }

    public static void refreshLearningItemList() {
        pressOptionMenu();
        pressRefreshOptionOnOptionMenu();
    }

    // RENAME SET

    public static void pressLearningItemSetTitleChangeIcon() {
        onView(withId(R.id.learning_item_set_name_change_icon)).perform(click());
    }

    public static void typeOutNewLearningItemSetTitleName(String newSetName) {
        onView(withId(R.id.learning_item_set_name_textbox)).perform(clearText(), typeText(newSetName));
    }

    public static void renameLearningItemSet(String newSetName) {
        pressLearningItemSetTitleChangeIcon();
        typeOutNewLearningItemSetTitleName(newSetName);
        pressLearningItemSetTitleChangeIcon();
    }

    // ADD SET

    public static void pressLearningItemSetSelector() {
        onView(withId(R.id.learning_item_set_selector)).perform(click());
    }

    public static void pressLearningItemSetSelectorOption(String optionText) {
        onView(withText(optionText)).perform(click());
    }

    public static void switchToLearningItemSet(String setName) {
        pressLearningItemSetSelector();
        pressLearningItemSetSelectorOption(setName);
    }

    public static void addNewLearningItemSet() {
        pressLearningItemSetSelector();
        pressLearningItemSetSelectorOption("Add new group");
    }

    public static void addNewLearningItemSet(String newSetName) {
        pressLearningItemSetSelector();
        pressLearningItemSetSelectorOption("Add new group");
        typeOutNewLearningItemSetTitleName(newSetName);
        pressLearningItemSetTitleChangeIcon();
    }
}
