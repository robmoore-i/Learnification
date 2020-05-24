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

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        guiTestWrapper = new GuiTestWrapper(activity);
        guiTestWrapper.beforeEach();
        AndroidTestObjectFactory testObjectFactory = new AndroidTestObjectFactory(activity);
        LearningItemSqlTableClient learningItemSqlTableClient = testObjectFactory.getLearningItemSqlTableClient();
        learningItemSqlTableClient.writeAll(Collections.singletonList(new LearningItem("อะไร", "what", "Thai")));
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

        UserSimulation.clickShowMeOnLearnification();

        UserSimulation.checkForLearnificationResponse();
    }

    @Test
    public void youGetAResponseAndAnotherLearnificationIsSentWhenYouClickNextOnALearnification() {
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.clickNextOnLearnification();

        UserSimulation.checkForLearnificationResponse();
        UserSimulation.checkForLearnification();
    }

    @Test
    public void notificationGetsCancelledWhenYouSubmitACorrectLearnificationResult() {
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.clickShowMeOnLearnification();
        UserSimulation.clickOnTickedLearnificationResult();

        UserSimulation.checkForLackOfLearnificationRelatedNotifications();
    }

    @Test
    public void notificationGetsCancelledWhenYouSubmitAnIncorrectLearnificationResult() {
        UserSimulation.pressLearnificationFastForwardButton();

        UserSimulation.clickShowMeOnLearnification();
        UserSimulation.clickOnCrossedLearnificationResult();

        UserSimulation.checkForLackOfLearnificationRelatedNotifications();
    }

//    @Test
//    public void ifThereAreLearningItemsThenYouGetALearnificationOnAppStartup() {
//    }
//
//    @Test
//    public void youGetALearnificationIfThereAreLearningItemsAndYouClickTheFastForwardButton() {
//    }
//
//    @Test
//    public void youDontGetALearnificationIfThereAreNoLearningItemsAndYouClickTheFastForwardButton() {
//    }
}
