package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class FromFileLearningItemStorageTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private TestJanitor testJanitor = new TestJanitor();
    private FromFileLearningItemStorage fromFileLearnificationStorage;

    @Before
    public void beforeEach() {
        fromFileLearnificationStorage = activityTestRule.getActivity().getFromFileLearnificationStorage();
        fromFileLearnificationStorage.rewrite(new ArrayList<>());
    }

    @After
    public void afterEach() {
        testJanitor.clearApp(activityTestRule);
    }

    @Test
    public void canCreateLearnifications() {
        fromFileLearnificationStorage.write(new LearningItem("L", "R"));

        List<LearningItem> learningItems = fromFileLearnificationStorage.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem.left, equalTo("L"));
        assertThat(learningItem.right, equalTo("R"));
    }

    @Test
    public void canCreateLearnificationsTwice() {
        fromFileLearnificationStorage.write(new LearningItem("L", "R"));
        fromFileLearnificationStorage.write(new LearningItem("X", "Y"));

        List<LearningItem> learningItems = fromFileLearnificationStorage.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem.left, equalTo("L"));
        assertThat(learningItem.right, equalTo("R"));

        learningItem = learningItems.get(1);
        assertThat(learningItem.left, equalTo("X"));
        assertThat(learningItem.right, equalTo("Y"));
    }

    @Test
    public void canCreateNewLearnificationAfterInitialRead() {
        fromFileLearnificationStorage.read();
        fromFileLearnificationStorage.write(new LearningItem("L", "R"));

        List<LearningItem> learningItems = fromFileLearnificationStorage.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem.left, equalTo("L"));
        assertThat(learningItem.right, equalTo("R"));
    }
}
