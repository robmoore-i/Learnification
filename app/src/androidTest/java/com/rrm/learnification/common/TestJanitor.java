package com.rrm.learnification.common;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import com.rrm.learnification.main.MainActivity;

class TestJanitor {
    private static final String PACKAGE_NAME = "com.rrm.learnification";

    void clearApp() {
        try {
            Runtime.getRuntime().exec(new String[]{"am", "force-stop", PACKAGE_NAME});
            clearAllNotifications();
            escapeNotificationsBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clearApp(ActivityTestRule<MainActivity> activityTestRule) {
        clearAppData(activityTestRule);
        clearApp();
        escapeNotificationsBar();
    }

    private void clearAppData(ActivityTestRule<MainActivity> activityTestRule) {
        activityTestRule.getActivity().clearData();
    }

    void clearAllNotifications() {
        try {
            int notificationTimeoutMs = 1000;
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            device.openNotification();
            device.wait(Until.hasObject(By.textStartsWith(PACKAGE_NAME)), notificationTimeoutMs);
            UiObject2 clearAll = device.findObject(By.text("CLEAR ALL"));
            clearAll.click();
            device.pressBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void escapeNotificationsBar() {
        try {
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            device.pressHome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}