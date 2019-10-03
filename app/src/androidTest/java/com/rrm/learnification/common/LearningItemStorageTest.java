package com.rrm.learnification.common;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearningItemStorage;

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
public class LearningItemStorageTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private TestJanitor testJanitor = new TestJanitor();
    private LearningItemStorage learningItemStorage;

    @Before
    public void beforeEach() {
        learningItemStorage = activityTestRule.getActivity().getLearningItemStorage();
        learningItemStorage.rewrite(new ArrayList<>());
    }

    @After
    public void afterEach() {
        testJanitor.clearApp(activityTestRule);
    }

    @Test
    public void canCreateLearnifications() {
        learningItemStorage.write(new LearningItem("L", "R"));

        List<LearningItem> learningItems = learningItemStorage.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem.left, equalTo("L"));
        assertThat(learningItem.right, equalTo("R"));
    }

    @Test
    public void canCreateLearnificationsTwice() {
        learningItemStorage.write(new LearningItem("L", "R"));
        learningItemStorage.write(new LearningItem("X", "Y"));

        List<LearningItem> learningItems = learningItemStorage.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem.left, equalTo("L"));
        assertThat(learningItem.right, equalTo("R"));

        learningItem = learningItems.get(1);
        assertThat(learningItem.left, equalTo("X"));
        assertThat(learningItem.right, equalTo("Y"));
    }

    @Test
    public void canCreateNewLearnificationAfterInitialRead() {
        learningItemStorage.read();
        learningItemStorage.write(new LearningItem("L", "R"));

        List<LearningItem> learningItems = learningItemStorage.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem.left, equalTo("L"));
        assertThat(learningItem.right, equalTo("R"));
    }
}
