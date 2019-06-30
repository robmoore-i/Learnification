package com.rrm.learnification;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.SearchCondition;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CreatesNotificationOnStartupTest {
    private static final String PACKAGE_NAME = "com.rrm.learnification";

    private final TestJanitor testJanitor = new TestJanitor();

    @After
    public void tearDown() {
        testJanitor.clearApp();
    }

    @Test
    public void whenYouOpenTheAppItCreatesANotification() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        startApp(device);

        assertNotificationCreated(device);
    }

    private void assertNotificationCreated(UiDevice device) {
        int notificationTimeoutMs = 1000;
        device.openNotification();
        SearchCondition<Boolean> condition = Until.hasObject(By.pkg(PACKAGE_NAME));
        assertTrue(device.wait(condition, notificationTimeoutMs));
    }

    private void startApp(UiDevice device) {
        int launchTimeoutMs = 5000;

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), launchTimeoutMs);

        // Launch the app
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        assert intent != null;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), launchTimeoutMs);
    }
}
