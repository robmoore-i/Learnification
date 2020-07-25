package com.rrm.learnification.gui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.learningitemstorage.LearningItemSqlTableClient;
import com.rrm.learnification.support.GuiTestWrapper;
import com.rrm.learnification.support.UserSimulation;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

@RunWith(AndroidJUnit4.class)
public class LearnificationTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private GuiTestWrapper guiTestWrapper;

    private LearningItemSqlTableClient learningItemSqlTableClient;

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        guiTestWrapper = new GuiTestWrapper(activity);
        guiTestWrapper.beforeEach();
        AndroidTestObjectFactory testObjectFactory = new AndroidTestObjectFactory(activity);
        learningItemSqlTableClient = testObjectFactory.getLearningItemSqlTableClient();
        learningItemSqlTableClient.writeAll(Collections.singletonList(new LearningItem("อะไร", "what", "default")));
    }

    @After
    public void afterEach() {
        guiTestWrapper.afterEach();
    }

    @Test
    public void youGetAResponseWhenAnsweringALearnification() {
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.respondToLearnification("x");

        UserSimulation.checkForLearnificationResponse();
    }

    @Test
    public void youGetAResponseWhenYouClickShowMeOnALearnification() {
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.pressShowMeOnLearnification();

        UserSimulation.checkForLearnificationResponse();
    }

    @Test
    public void youGetAResponseAndAnotherLearnificationIsSentWhenYouClickNextOnALearnification() {
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.pressNextOnLearnification();

        UserSimulation.checkForLearnificationResponse();
        UserSimulation.checkForLearnification();
    }

    @Test
    public void notificationGetsCancelledWhenYouSubmitACorrectLearnificationResult() {
        UserSimulation.clearNotifications(activityTestRule.getActivity());
        UserSimulation.waitASecond();
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.pressShowMeOnLearnification();
        UserSimulation.pressOnTickedLearnificationResult();

        UserSimulation.checkForLackOfLearnificationRelatedNotifications();
    }

    @Test
    public void notificationGetsCancelledWhenYouSubmitAnIncorrectLearnificationResult() {
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.pressShowMeOnLearnification();
        UserSimulation.pressOnCrossedLearnificationResult();

        UserSimulation.checkForLackOfLearnificationRelatedNotifications();
    }

    @Test
    public void ifThereAreLearningItemsThenYouGetALearnificationOnAppStartup() {
        try {
            // If there were learnifications when the app was opened, and if the feature is working, then this will pass
            UserSimulation.checkForLearnification();
        } catch (Exception e) {
            // If it fails, it could mean that there were no learnifications when the app was opened. So add one and try again.
            // If this fails, then the feature is not working.
            UserSimulation.closeNotificationPulldown();
            UserSimulation.addLearningItem("krai", "who?");
            UserSimulation.waitASecond();
            UserSimulation.closeApp();
            UserSimulation.waitASecond();
            UserSimulation.peekAtRunningApps();
            UserSimulation.waitASecond();
            UserSimulation.openAppFromHome();
            UserSimulation.waitASecond();
            UserSimulation.checkForLearnification();
        }
    }

    @Test
    public void youGetALearnificationIfThereAreLearningItemsAndYouClickTheFastForwardButton() {
        UserSimulation.clearNotifications(activityTestRule.getActivity());
        UserSimulation.waitASecond();

        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.checkForLearnification();
    }

    @Test
    public void youDontGetALearnificationIfThereAreNoLearningItemsAndYouClickTheFastForwardButton() {
        learningItemSqlTableClient.clearEverything();
        UserSimulation.clearNotifications(activityTestRule.getActivity());
        UserSimulation.waitASecond();
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.checkForLackOfLearnificationRelatedNotifications();
    }
}
