package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearningItemSqlRecordStore;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LearningItemSqlTableClientTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private DatabaseTestWrapper databaseTestWrapper;

    private LearningItemSqlTableClient learningItemSqlTableClient;

    @Before
    public void before() {
        databaseTestWrapper = new DatabaseTestWrapper(activityTestRule.getActivity());
        databaseTestWrapper.beforeEach();
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        LearningItemSqlRecordStore learningItemSqlRecordStore = new LearningItemSqlRecordStore(androidTestObjectFactory.getLearnificationAppDatabase(), "default");
        learningItemSqlRecordStore.writeAll(Arrays.asList(
                new LearningItem("1", "a"),
                new LearningItem("2", "b"),
                new LearningItem("3", "c"),
                new LearningItem("4", "d")
        ));

        learningItemSqlTableClient = new LearningItemSqlTableClient(androidTestObjectFactory.getLearnificationAppDatabase());
    }

    @After
    public void afterEach() {
        databaseTestWrapper.afterEach();
    }

    @Test
    public void canCountTheNumberOfLearningItemSetsWhenIts1() {
        assertThat(learningItemSqlTableClient.numberOfDistinctLearningItemSets(), equalTo(1));
    }

    @Test
    public void returnsDefaultAsTheMostPopulousLearningItemSetNameIfThereAreNoLearningItems() {
        learningItemSqlTableClient.clearEverything();
        assertThat(learningItemSqlTableClient.mostPopulousLearningItemSetName(), equalTo("default"));
    }
}