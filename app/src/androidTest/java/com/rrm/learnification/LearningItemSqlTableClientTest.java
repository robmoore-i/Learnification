package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LearningItemSqlTableClientTest {
    private final List<LearningItem> thaiLearningItems = Arrays.asList(
            new LearningItem("sa-wad-dee-kab", "hello"),
            new LearningItem("aroi", "delicious"),
            new LearningItem("ayam", "ayam")
    );
    private final List<LearningItem> oneAndTwo = Arrays.asList(new LearningItem("1", "a"), new LearningItem("2", "b"));

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
        learningItemSqlTableClient = new LearningItemSqlTableClient(androidTestObjectFactory.getLearnificationAppDatabase());
        learningItemSqlTableClient.clearEverything();
    }

    @After
    public void afterEach() {
        databaseTestWrapper.afterEach();
        ArrayList<LearningItem> toCleanUp = new ArrayList<>();
        toCleanUp.addAll(oneAndTwo);
        toCleanUp.addAll(thaiLearningItems);
        String[] setNames = {"default", "Chinese", "Thai"};
        for (String setName : setNames) {
            sqlLearningItemSetRecordStore = recordStore(setName);
            for (LearningItem learningItem : toCleanUp) {
                sqlLearningItemSetRecordStore.delete(learningItem);
            }
        }
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

    private SqlLearningItemSetRecordStore recordStore(String learningItemSetName) {
        return new SqlLearningItemSetRecordStore(logger, androidTestObjectFactory.getLearnificationAppDatabase(), learningItemSetName);
    }
}