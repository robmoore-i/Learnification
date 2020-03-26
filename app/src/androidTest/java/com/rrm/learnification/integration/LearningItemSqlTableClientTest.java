package com.rrm.learnification.integration;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.support.DatabaseTestWrapper;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class LearningItemSqlTableClientTest {
    private final List<LearningItemText> thaiLearningItems = Arrays.asList(
            new LearningItemText("sa-wad-dee-kab", "hello"),
            new LearningItemText("aroi", "delicious"),
            new LearningItemText("ayam", "ayam")
    );
    private final List<LearningItemText> oneAndTwo = Arrays.asList(new LearningItemText("1", "a"), new LearningItemText("2", "b"));
    private final LearningItem krabLearningItem = new LearningItem("krab", "krab", "Thai");
    private final List<LearningItem> mixedLearningItems = Arrays.asList(
            new LearningItem("ni", "you", "Chinese"),
            new LearningItem("wo", "me", "Chinese"),
            new LearningItem("ka", "ka", "Thai"),
            krabLearningItem,
            new LearningItem("dee", "good", "Thai"),
            new LearningItem("a", "b", "default")
    );

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private DatabaseTestWrapper databaseTestWrapper;

    private AndroidLogger logger = new AndroidLogger();

    private LearningItemSqlTableClient learningItemSqlTableClient;
    private SqlLearningItemSetRecordStore sqlLearningItemSetRecordStore;
    private AndroidTestObjectFactory androidTestObjectFactory;

    @Before
    public void before() {
        databaseTestWrapper = new DatabaseTestWrapper(activityTestRule.getActivity());
        databaseTestWrapper.beforeEach();
        androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        learningItemSqlTableClient = new LearningItemSqlTableClient(logger, androidTestObjectFactory.getLearnificationAppDatabase());
        learningItemSqlTableClient.clearEverything();
    }

    @After
    public void afterEach() {
        databaseTestWrapper.afterEach();
        ArrayList<LearningItemText> toCleanUp = new ArrayList<>();
        toCleanUp.addAll(oneAndTwo);
        toCleanUp.addAll(thaiLearningItems);
        String[] setNames = {"default", "Chinese", "Thai"};
        for (String setName : setNames) {
            sqlLearningItemSetRecordStore = recordStore(setName);
            for (LearningItemText learningItemText : toCleanUp) {
                try {
                    sqlLearningItemSetRecordStore.delete(learningItemText);
                } catch (AssertionError ignored) {
                }
            }
        }
        learningItemSqlTableClient.deleteAll(mixedLearningItems);
    }

    @Test
    public void canCountTheNumberOfLearningItemSetsWhenIts1() {
        sqlLearningItemSetRecordStore = recordStore("default");
        sqlLearningItemSetRecordStore.writeAll(oneAndTwo);

        assertThat(learningItemSqlTableClient.numberOfDistinctLearningItemSets(), equalTo(1));
    }

    @Test
    public void canCountTheNumberOfLearningItemSetsWhenIts2() {
        sqlLearningItemSetRecordStore = recordStore("Chinese");
        sqlLearningItemSetRecordStore.writeAll(oneAndTwo);
        sqlLearningItemSetRecordStore = recordStore("Thai");
        sqlLearningItemSetRecordStore.writeAll(oneAndTwo);

        assertThat(learningItemSqlTableClient.numberOfDistinctLearningItemSets(), equalTo(2));
    }

    @Test
    public void returnsDefaultAsTheMostPopulousLearningItemSetNameIfThereAreNoLearningItems() {
        assertThat(learningItemSqlTableClient.mostPopulousLearningItemSetName(), equalTo("default"));
    }

    @Test
    public void returnsThaiAsTheMostPopulousIfItIsTheOnlySet() {
        sqlLearningItemSetRecordStore = recordStore("Thai");

        sqlLearningItemSetRecordStore.writeAll(thaiLearningItems);

        assertThat(learningItemSqlTableClient.mostPopulousLearningItemSetName(), equalTo("Thai"));
    }

    @Test
    public void canCountThreeDistinctLearningItemSets() {
        learningItemSqlTableClient.writeAll(mixedLearningItems);

        assertEquals(3, learningItemSqlTableClient.numberOfDistinctLearningItemSets());
    }

    @Test
    public void returnsTheListOfLearningItemSetsOrderedBySize() {
        learningItemSqlTableClient.writeAll(mixedLearningItems);

        List<String> learningItemSetNames = learningItemSqlTableClient.orderedLearningItemSetNames();

        assertThat(learningItemSetNames, equalTo(Arrays.asList("Thai", "Chinese", "default")));
    }

    @Test
    public void alwaysIncludesDefaultInTheListOfLearningItemSetsIfThereAreOtherwiseNone() {
        assertThat(learningItemSqlTableClient.numberOfDistinctLearningItemSets(), equalTo(1));
        assertThat(learningItemSqlTableClient.orderedLearningItemSetNames(), hasItem("default"));
        assertThat(learningItemSqlTableClient.orderedLearningItemSetNames().size(), equalTo(1));
    }

    @Test
    public void defaultIsNotIncludedIfThereAreOtherLearningItemSets() {
        learningItemSqlTableClient.writeAll(Collections.singletonList(krabLearningItem));

        assertThat(learningItemSqlTableClient.numberOfDistinctLearningItemSets(), equalTo(1));
        assertThat(learningItemSqlTableClient.orderedLearningItemSetNames(), not(hasItem("default")));
        assertThat(learningItemSqlTableClient.orderedLearningItemSetNames().size(), equalTo(1));
        assertThat(learningItemSqlTableClient.orderedLearningItemSetNames(), equalTo(Collections.singletonList("Thai")));
    }

    private SqlLearningItemSetRecordStore recordStore(String learningItemSetName) {
        return new SqlLearningItemSetRecordStore(logger, androidTestObjectFactory.getLearnificationAppDatabase(), learningItemSetName);
    }
}