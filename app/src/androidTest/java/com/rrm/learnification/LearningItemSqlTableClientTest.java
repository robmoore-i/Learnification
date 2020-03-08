package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearningItemSetSqlRecordStore;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
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
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private DatabaseTestWrapper databaseTestWrapper;

    private LearningItemSqlTableClient learningItemSqlTableClient;
    private AndroidLogger logger = new AndroidLogger();
    private LearningItemSetSqlRecordStore learningItemSetSqlRecordStore;
    private AndroidTestObjectFactory androidTestObjectFactory;
    private final List<LearningItem> oneAndTwo = Arrays.asList(new LearningItem("1", "a"), new LearningItem("2", "b"));

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
            learningItemSetSqlRecordStore = recordStore(setName);
            for (LearningItem learningItem : toCleanUp) {
                learningItemSetSqlRecordStore.delete(learningItem);
            }
        }
    }

    @Test
    public void canCountTheNumberOfLearningItemSetsWhenIts1() {
        learningItemSetSqlRecordStore = recordStore("default");
        learningItemSetSqlRecordStore.writeAll(oneAndTwo);

        assertThat(learningItemSqlTableClient.numberOfDistinctLearningItemSets(), equalTo(1));
    }

    @Test
    public void canCountTheNumberOfLearningItemSetsWhenIts2() {
        learningItemSetSqlRecordStore = recordStore("Chinese");
        learningItemSetSqlRecordStore.writeAll(oneAndTwo);
        learningItemSetSqlRecordStore = recordStore("Thai");
        learningItemSetSqlRecordStore.writeAll(oneAndTwo);

        assertThat(learningItemSqlTableClient.numberOfDistinctLearningItemSets(), equalTo(2));
    }

    @Test
    public void returnsDefaultAsTheMostPopulousLearningItemSetNameIfThereAreNoLearningItems() {
        assertThat(learningItemSqlTableClient.mostPopulousLearningItemSetName(), equalTo("default"));
    }

    @Test
    public void returnsThaiAsTheMostPopulousIfItIsTheOnlySet() {
        learningItemSetSqlRecordStore = recordStore("Thai");
        learningItemSetSqlRecordStore.writeAll(thaiLearningItems);

        assertThat(learningItemSqlTableClient.mostPopulousLearningItemSetName(), equalTo("Thai"));
    }

    private LearningItemSetSqlRecordStore recordStore(String learningItemSetName) {
        return new LearningItemSetSqlRecordStore(logger, androidTestObjectFactory.getLearnificationAppDatabase(), learningItemSetName);
    }
}