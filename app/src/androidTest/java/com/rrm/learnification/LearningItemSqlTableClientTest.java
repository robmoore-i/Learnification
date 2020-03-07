package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearningItemSqlRecordStore;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.PersistentItemStore;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LearningItemSqlTableClientTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private PersistentItemStore<LearningItem> learningPersistentItemStore;
    private List<LearningItem> originalLearningItems;
    private LearningItemSqlTableClient learningItemSqlTableClient;

    @Before
    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        learningItemSqlTableClient = new LearningItemSqlTableClient(androidTestObjectFactory.getLearnificationAppDatabase());

        learningPersistentItemStore = androidTestObjectFactory.getLearningItemStorage();
        originalLearningItems = learningItemSqlTableClient.allRecords();
        learningItemSqlTableClient.clearEverything();

        LearningItemSqlRecordStore learningItemSqlRecordStore = new LearningItemSqlRecordStore(androidTestObjectFactory.getLearnificationAppDatabase(), "default");
        learningItemSqlRecordStore.writeAll(Arrays.asList(
                new LearningItem("1", "a"),
                new LearningItem("2", "b"),
                new LearningItem("3", "c"),
                new LearningItem("4", "d")
        ));
    }

    @After
    public void afterEach() {
        learningPersistentItemStore.rewrite(originalLearningItems);
    }

    @Test
    public void canCountTheNumberOfLearningItemSetsWhenIts1() {
        assertThat(learningItemSqlTableClient.numberOfDistinctLearningItemSets(), equalTo(1));
    }
}