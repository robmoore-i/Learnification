package com.rrm.learnification.support;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.widget.NumberPicker;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import com.rrm.learnification.R;
import com.rrm.learnification.learningitemseteditor.learningitemsetselector.LearningItemSetSelectorAdaptor;

import org.hamcrest.Matcher;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.rrm.learnification.support.CustomMatcher.withToolbarTitle;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * This class contains UI controls for all the actions that I expect a user to take in the
 * app. Of course, this is a very limited set of what they are capable of, however, it can grow
 * as those assumptions become invalidated.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class UserSimulation {

    // ANDROID

    public static void pressBack() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressBack();
    }

    public static void pressHome() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();
    }

    public static void openAppFromHome() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Swipes right to get to the app in the app drawer.
        // This is based on my personal phone's particular set up!
        int y = device.getDisplayHeight() / 2;
        device.swipe(device.getDisplayWidth() - 10, y, 10, y, 20);
        UserSimulation.waitASecond();
        device.findObject(By.descContains("Learnification")).click();
    }

    public static void peekAtRunningApps() {
        try {
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            device.pressRecentApps();
            UserSimulation.waitACoupleOfSeconds();
            device.pressRecentApps();
        } catch (RemoteException e) {
            throw new RuntimeException("Something went wrong when checking recently opened apps", e);
        }
    }

    public static void closeApp() {
        try {
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            device.pressRecentApps();
            UserSimulation.waitASecond();
            device.findObject(By.descContains("Learnification")).swipe(Direction.RIGHT, 1.0f);
            UserSimulation.waitASecond();
        } catch (RemoteException e) {
            throw new RuntimeException("Something went wrong when closing app", e);
        }
    }

    public static void clearNotifications(Activity activity) {
        activity.getSystemService(NotificationManager.class).cancelAll();
    }

    public static void closeNotificationPulldown() {
        pressBack();
    }

    // LEARNIFICATION

    public static void respondToLearnification(String input) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        if (!device.wait(Until.hasObject(By.text("Learn!")), 1000)) {
            throw new RuntimeException("Couldn't find learnification");
        }
        if (!device.wait(Until.hasObject(By.text("RESPOND")), 1000)) {
            throw new RuntimeException("Couldn't find learnification action for responding with text");
        }
        device.findObject(By.text("RESPOND")).click();
        KeyCharacterMap keyCharacterMap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD);
        KeyEvent[] events = keyCharacterMap.getEvents(input.toCharArray());
        for (KeyEvent event : events) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                device.pressKeyCode(event.getKeyCode(), event.getMetaState());
            }
        }
        device.findObject(By.desc("Send")).click();
    }

    public static void pressShowMeOnLearnification() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        if (!device.wait(Until.hasObject(By.text("Learn!")), 1000)) {
            throw new RuntimeException("Couldn't find learnification");
        }
        if (!device.wait(Until.hasObject(By.text("SHOW ME")), 1000)) {
            throw new RuntimeException("Couldn't find learnification action for showing learnification answer");
        }
        device.findObject(By.text("SHOW ME")).click();
    }

    public static void pressNextOnLearnification() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        if (!device.wait(Until.hasObject(By.text("Learn!")), 1000)) {
            throw new RuntimeException("Couldn't find learnification");
        }
        if (!device.wait(Until.hasObject(By.text("NEXT")), 1000)) {
            throw new RuntimeException("Couldn't find learnification action for showing learnification answer and triggering next learnification");
        }
        device.findObject(By.text("NEXT")).click();
    }

    public static void pressOnTickedLearnificationResult() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        if (!device.wait(Until.hasObject(By.textStartsWith("Next one in ")), 1000)) {
            throw new RuntimeException("Couldn't find learnification response");
        }
        if (!device.wait(Until.hasObject(By.text("MY ANSWER WAS ✅")), 1000)) {
            throw new RuntimeException("Couldn't find learnification action for showing learnification answer and triggering next learnification");
        }
        device.findObject(By.text("MY ANSWER WAS ✅")).click();
    }

    public static void pressOnCrossedLearnificationResult() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        if (!device.wait(Until.hasObject(By.textStartsWith("Next one in ")), 1000)) {
            throw new RuntimeException("Couldn't find learnification response");
        }
        if (!device.wait(Until.hasObject(By.text("MY ANSWER WAS ❌")), 1000)) {
            throw new RuntimeException("Couldn't find learnification action for showing learnification answer and triggering next learnification");
        }
        device.findObject(By.text("MY ANSWER WAS ❌")).click();
    }

    public static void checkForLearnification() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        if (!device.wait(Until.hasObject(By.text("Learn!")), 1000)) {
            throw new RuntimeException("Couldn't find learnification");
        }
    }

    public static void checkForLackOfLearnificationRelatedNotifications() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        if (device.wait(Until.hasObject(By.text("Learnification")), 1000)) {
            throw new RuntimeException("Unexpectedly found learnification");
        }
    }

    public static void checkForLearnificationResponse() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        if (!device.wait(Until.hasObject(By.textStartsWith("Next one in ")), 1000)) {
            throw new RuntimeException("Couldn't find learnification response");
        }
    }

    // ADD LEARNING ITEM

    public static void focusLeftInputForNewLearningItem() {
        onView(ViewMatchers.withId(R.id.left_input)).perform(click());
    }

    public static void typeOutNewLearningItemIntoTextFields(String left, String right) {
        onView(ViewMatchers.withId(R.id.left_input)).perform(typeText(left));
        onView(withId(R.id.right_input)).perform(typeText(right));
    }

    public static void pressAddLearningItemButton() {
        onView(withId(R.id.edit_learning_item_list_button)).perform(click());
        closeSoftKeyboard();
    }

    public static void addLearningItem(String left, String right) {
        typeOutNewLearningItemIntoTextFields(left, right);
        pressAddLearningItemButton();
    }

    public static void checkForLearningItem(String left, String right) {
        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(left + " - " + right))).check(matches(isDisplayed()));
    }

    // UPDATE LEARNING ITEM

    public static void typeOutLearningItemListEntryUpdate(String oldLeft, String oldRight, String newLeft, String newRight) {
        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(oldLeft + " - " + oldRight)))
                .perform(clearText(), typeText(newLeft + " - " + newRight));
        closeSoftKeyboard();
    }

    public static void typeOutLearningItemListEntryUpdate(String oldLeft, String oldRight, String extraText) {
        onView(allOf(withParent(withId(R.id.learning_item_list)), withText(oldLeft + " - " + oldRight)))
                .perform(typeText(extraText));
        closeSoftKeyboard();
    }

    public static void pressUpdateLearningItemButton() {
        onView(withId(R.id.edit_learning_item_list_button)).perform(click());
    }

    public static void updateLearningItem(String oldLeft, String oldRight, String newLeft, String newRight) {
        typeOutLearningItemListEntryUpdate(oldLeft, oldRight, newLeft, newRight);
        pressUpdateLearningItemButton();
    }

    public static void updateLearningItem(String oldLeft, String oldRight, String extraText) {
        typeOutLearningItemListEntryUpdate(oldLeft, oldRight, extraText);
        pressUpdateLearningItemButton();
    }

    // DELETE LEARNING ITEM

    public static void swipeLearningItem(String left, String right) {
        onView(withText(equalTo(left + " - " + right))).perform(swipeLeft());
    }

    public static void deleteLearningItem(String left, String right) {
        swipeLearningItem(left, right);
    }

    public static int countLearningItems(Activity activity) {
        RecyclerView recyclerView = activity.findViewById(R.id.learning_item_list);
        return recyclerView.getChildCount();
    }

    // LOG DUMP

    public static void switchToLogDumpScreen() {
        pressOptionMenu();
        pressLogDumpOptionOnOptionMenu();
    }

    public static void pressLogDumpOptionOnOptionMenu() {
        onView(withText(R.string.options_menu_logs)).perform(click());
    }

    // JOB DUMP

    public static void switchToJobDumpScreen() {
        pressOptionMenu();
        pressJobDumpOptionOnOptionMenu();
    }

    private static void pressJobDumpOptionOnOptionMenu() {
        onView(withText(R.string.options_menu_jobs)).perform(click());
    }

    // SETTINGS

    public static void pressOptionMenu() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
    }

    public static void pressSettingsInNavMenu() {
        onView(withId(R.id.navigation_settings)).perform(click());
    }

    public static void switchToSettingsScreen() {
        pressSettingsInNavMenu();
    }

    public static void spinLearnificationDelaySpinnerToValue(Activity activity, int newLearnificationDelayValueMinutes) {
        NumberPicker picker = activity.findViewById(R.id.learnification_delay_input);
        picker.setValue(newLearnificationDelayValueMinutes);
    }

    public static void selectPromptStrategyOption(String rightToLeft_leftToRight_or_mixed) {
        if ("rightToLeft".equals(rightToLeft_leftToRight_or_mixed)) {
            onView(withId(R.id.right_to_left)).perform(click());
        } else if ("leftToRight".equals(rightToLeft_leftToRight_or_mixed)) {
            onView(withId(R.id.left_to_right)).perform(click());
        } else if ("mixed".equals(rightToLeft_leftToRight_or_mixed)) {
            onView(withId(R.id.mixed_left_and_right)).perform(click());
        } else {
            throw new IllegalArgumentException("Expected one of 'rightToLeft', 'leftToRight' or 'mixed'");
        }
    }

    public static void saveSettingsAndReturnToSetEditor() {
        onView(withId(R.id.save_settings_button)).perform(click());
    }

    // TOOLBAR

    public static void pressLearnificationFastForwardButton() {
        onView(withId(R.id.toolbar_button)).perform(click());
    }

    public static void checkToolbarTitle(Matcher<String> toolbarTitleMatcher) {
        onView(allOf(withId(R.id.toolbar), withToolbarTitle(toolbarTitleMatcher))).check(matches(isDisplayed()));
    }

    public static void checkForFastForwardButton() {
        onView(allOf(withId(R.id.toolbar_button), withText(">>"))).check(matches(isDisplayed()));
    }

    // REFRESH

    public static void pressRefreshOptionOnOptionMenu() {
        onView(withText(R.string.options_menu_refresh)).perform(click());
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
        pressLearningItemSetSelectorOption(LearningItemSetSelectorAdaptor.addNewSetOptionText);
    }

    public static void addNewLearningItemSet(String newSetName) {
        pressLearningItemSetSelector();
        pressLearningItemSetSelectorOption(LearningItemSetSelectorAdaptor.addNewSetOptionText);
        typeOutNewLearningItemSetTitleName(newSetName);
        pressLearningItemSetTitleChangeIcon();
    }

    // WAITING

    public static void waitASecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitACoupleOfSeconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitSomeSeconds() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
