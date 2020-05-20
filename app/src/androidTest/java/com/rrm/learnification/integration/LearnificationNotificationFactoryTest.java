package com.rrm.learnification.integration;

import android.app.Notification;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.learnification.creation.LearnificationNotificationFactory;
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
public class LearnificationNotificationFactoryTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private LearnificationNotificationFactory learnificationNotificationFactory;

    @Before
    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        learnificationNotificationFactory = androidTestObjectFactory.getLearnificationNotificationFactory();
    }

    @Test
    public void itGeneratesLearnificationWithABundleContainingTheNotificationType() {
        Notification learnification = learnificationNotificationFactory.createLearnification(new LearnificationText("a", "b"));

        assertThat(learnification.extras.getString(NotificationType.NOTIFICATION_TYPE_EXTRA_NAME), equalTo(NotificationType.LEARNIFICATION));
    }

    @Test
    public void itGeneratesLearnificationWithPendingIntents() {
        String[] expectedPendingIntentTitles = {"Respond", "Show me", "Next"};
        Notification learnification = learnificationNotificationFactory.createLearnification(new LearnificationText("a", "b"));

        List<String> pendingIntentTitles = Arrays.stream(learnification.actions).map(action -> action.title.toString()).collect(Collectors.toList());

        assertThat(pendingIntentTitles, hasItems(expectedPendingIntentTitles));
        assertEquals(expectedPendingIntentTitles.length, learnification.actions.length);
    }
}
