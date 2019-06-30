package com.rrm.learnification;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

class TestJanitor {
    private static final String PACKAGE_NAME = "com.rrm.learnification";

    void clearApp() {
        try {
            Runtime.getRuntime().exec(new String[]{"am", "force-stop", PACKAGE_NAME});
            clearAllNotifications();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clearApp(ActivityTestRule<MainActivity> activityTestRule) {
        activityTestRule.getActivity().clearData();
        clearApp();
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
}
