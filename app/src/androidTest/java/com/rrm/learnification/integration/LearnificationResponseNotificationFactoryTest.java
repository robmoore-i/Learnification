package com.rrm.learnification.integration;

import android.app.Notification;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.learnification.response.NotificationTextContent;
import com.rrm.learnification.learnificationresponse.creation.LearnificationResponseNotificationFactory;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.notification.NotificationType;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LearnificationResponseNotificationFactoryTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private LearnificationResponseNotificationFactory learnificationResponseNotificationFactory;

    @Before
    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        learnificationResponseNotificationFactory = androidTestObjectFactory.getAndroidNotificationFactory();
    }

    @Test
    public void itGeneratesLearnificationResponseWithABundleContainingTheNotificationType() {
        Notification learnificationResponse = learnificationResponseNotificationFactory.createLearnificationResponse(new NotificationTextContent("a", "b"), "", "");

        assertThat(learnificationResponse.extras.getString(NotificationType.NOTIFICATION_TYPE_EXTRA_NAME), equalTo(NotificationType.LEARNIFICATION_RESPONSE));
    }

    @Test
    public void notificationResponsesAreUpdatedWithResultCapturePendingIntents() {
        String[] expectedPendingIntentTitles = {"My answer was ✅", "My answer was ❌"};
        NotificationTextContent notificationTextContent = new NotificationTextContent("Some Title", "Some Text");
        Notification learnificationResponse = learnificationResponseNotificationFactory.createLearnificationResponse(notificationTextContent, "Given", "Expected");

        List<String> pendingIntentTitles = Arrays.stream(learnificationResponse.actions).map(action -> action.title.toString()).collect(Collectors.toList());

        assertThat(pendingIntentTitles, hasItems(expectedPendingIntentTitles));
        assertEquals(expectedPendingIntentTitles.length, learnificationResponse.actions.length);
    }
}
