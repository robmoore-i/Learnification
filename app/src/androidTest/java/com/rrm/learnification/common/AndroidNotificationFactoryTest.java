package com.rrm.learnification.common;

import android.app.Notification;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.notification.AndroidNotificationFactory;
import com.rrm.learnification.notification.NotificationType;
import com.rrm.learnification.response.ResponseNotificationContent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class AndroidNotificationFactoryTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private AndroidNotificationFactory androidNotificationFactory;

    @Before
    public void beforeEach() {
        androidNotificationFactory = activityTestRule.getActivity().getAndroidNotificationFactory();
    }

    @Test
    public void itGeneratesLearnificationWithABundleContainingTheNotificationType() {
        Notification learnification = androidNotificationFactory.createLearnification(new LearnificationText("a", "b", "yo"));

        assertThat(learnification.extras.getString(AndroidNotificationFactory.NOTIFICATION_TYPE), equalTo(NotificationType.LEARNIFICATION));
    }

    @Test
    public void itGeneratesLearnificationResponseWithABundleContainingTheNotificationType() {
        Notification learnification = androidNotificationFactory.createLearnificationResponse(new ResponseNotificationContent("a", "b"));

        assertThat(learnification.extras.getString(AndroidNotificationFactory.NOTIFICATION_TYPE), equalTo(NotificationType.LEARNIFICATION_RESPONSE));
    }
}
