package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearningItemSetSqlRecordStore;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class LearningItemSetSqlRecordStoreTest {
    private final LearningItem[] asianLearningItems = {
            new LearningItem("frog", "gemba"),
            new LearningItem("phone", "machine brain"),
            new LearningItem("good", "dee")
    };
    private final LearningItem[] europeanLearningItems = {
            new LearningItem("der", "the"),
            new LearningItem("gamarjoba", "hello"),
            new LearningItem("sans", "without")
    };
    private final LearningItem deletedLearningItem = new LearningItem("l-test-replace", "r-test-replace");
    private final LearningItem replacementLearningItem = new LearningItem("l-test-replacement", "r-test-replacement");
    private final LearningItem writtenLearningItem = new LearningItem("l-test-write", "r-test-write");

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private DatabaseTestWrapper databaseTestWrapper;

    private static final String LEARNING_ITEM_SET_DEFAULT = "default";
    private static final String LEARNING_ITEM_SET_EUROPEAN = "european";

    private final AndroidLogger logger = new AndroidLogger();
    private LearningItemSetSqlRecordStore euroLearningItemRecordStore;
    private LearningItemSetSqlRecordStore asianLearningItemRecordStore;

    @Before
    public void beforeEach() {
        databaseTestWrapper = new DatabaseTestWrapper(activityTestRule.getActivity());
        databaseTestWrapper.beforeEach();
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        asianLearningItemRecordStore = new LearningItemSetSqlRecordStore(logger, androidTestObjectFactory.getLearnificationAppDatabase(), LEARNING_ITEM_SET_DEFAULT);
        euroLearningItemRecordStore = new LearningItemSetSqlRecordStore(logger, androidTestObjectFactory.getLearnificationAppDatabase(), LEARNING_ITEM_SET_EUROPEAN);
        asianLearningItemRecordStore.writeAll(Arrays.asList(asianLearningItems));
        euroLearningItemRecordStore.writeAll(Arrays.asList(europeanLearningItems));
    }

    @After
    public void afterEach() {
        databaseTestWrapper.afterEach();
        ArrayList<LearningItem> toCleanUp = new ArrayList<>();
        toCleanUp.addAll(Arrays.asList(asianLearningItems));
        toCleanUp.addAll(Arrays.asList(europeanLearningItems));
        toCleanUp.add(deletedLearningItem);
        toCleanUp.add(replacementLearningItem);
        toCleanUp.add(writtenLearningItem);
        for (LearningItem learningItem : toCleanUp) {
            asianLearningItemRecordStore.delete(learningItem);
            euroLearningItemRecordStore.delete(learningItem);
        }
    }

    @Test
    public void itReadsOnlyFromTheAssociatedSet() {
        assertThat(asianLearningItemRecordStore.readAll(), allOf(hasItems(asianLearningItems), not(hasItems(europeanLearningItems))));
    }

    @Test
    public void itWritesALearningItemOnlyInTheAssociatedSet() {
        LearningItem additionalLearningItem = writtenLearningItem;
        euroLearningItemRecordStore.write(additionalLearningItem);

        assertThat(asianLearningItemRecordStore.readAll(), allOf(hasItems(asianLearningItems), not(hasItems(europeanLearningItems))));
        assertThat(euroLearningItemRecordStore.readAll(), allOf(hasItems(europeanLearningItems), hasItems(additionalLearningItem), not(hasItems(asianLearningItems))));
    }

    @Test
    public void itReplacesALearningItemOnlyInTheAssociatedSet() {
        LearningItem replaced = deletedLearningItem;
        LearningItem replacement = replacementLearningItem;
        asianLearningItemRecordStore.write(replaced);
        euroLearningItemRecordStore.write(replaced);

        euroLearningItemRecordStore.replace(replaced, replacement);

        assertThat(asianLearningItemRecordStore.readAll(), hasItem(replaced));
        assertThat(euroLearningItemRecordStore.readAll(), hasItem(replacement));
    }

    @Test
    public void itDeletesALearningItemOnlyInTheAssociatedSet() {
        LearningItem deleted = deletedLearningItem;
        asianLearningItemRecordStore.write(deleted);
        euroLearningItemRecordStore.write(deleted);

        asianLearningItemRecordStore.delete(deleted);

        assertThat(asianLearningItemRecordStore.readAll(), not(hasItem(deleted)));
        assertThat(euroLearningItemRecordStore.readAll(), hasItem(deleted));
    }

    @Test
    public void itDeletesAllLearningItemsOnlyInTheAssociatedSet() {
        euroLearningItemRecordStore.deleteAll();

        assertThat(euroLearningItemRecordStore.readAll(), empty());
        assertThat(asianLearningItemRecordStore.readAll(), hasItems(asianLearningItems));
    }
}