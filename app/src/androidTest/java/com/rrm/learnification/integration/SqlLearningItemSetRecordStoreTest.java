package com.rrm.learnification.integration;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.support.IntegrationTestWrapper;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class SqlLearningItemSetRecordStoreTest {
    private static final String LEARNING_ITEM_SET_DEFAULT = "default";
    private static final String LEARNING_ITEM_SET_EUROPEAN = "european";

    private final LearningItem[] asianLearningItems = {
            new LearningItem("frog", "gemba", LEARNING_ITEM_SET_DEFAULT),
            new LearningItem("phone", "machine brain", LEARNING_ITEM_SET_DEFAULT),
            new LearningItem("good", "dee", LEARNING_ITEM_SET_DEFAULT)
    };
    private final LearningItem[] europeanLearningItems = {
            new LearningItem("der", "the", LEARNING_ITEM_SET_EUROPEAN),
            new LearningItem("gamarjoba", "hello", LEARNING_ITEM_SET_EUROPEAN),
            new LearningItem("sans", "without", LEARNING_ITEM_SET_EUROPEAN)
    };
    private final LearningItem deletedLearningItem = new LearningItem("l-test-replace-delete", "r-test-replace-delete", "default");
    private final LearningItem replacementLearningItem = new LearningItem("l-test-replacement", "r-test-replacement", LEARNING_ITEM_SET_EUROPEAN);
    private final LearningItem writtenLearningItem = new LearningItem("l-test-write", "r-test-write", "default");

    private final AndroidLogger logger = new AndroidLogger();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IntegrationTestWrapper guiTestWrapper;
    private SqlLearningItemSetRecordStore asianLearningItemRecordStore;
    private SqlLearningItemSetRecordStore euroLearningItemRecordStore;

    @Before
    public void beforeEach() {
        guiTestWrapper = new IntegrationTestWrapper(activityTestRule.getActivity());
        guiTestWrapper.beforeEach();
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        asianLearningItemRecordStore = new SqlLearningItemSetRecordStore(logger, androidTestObjectFactory.getLearnificationAppDatabase(),
                LEARNING_ITEM_SET_DEFAULT);
        euroLearningItemRecordStore = new SqlLearningItemSetRecordStore(logger, androidTestObjectFactory.getLearnificationAppDatabase(),
                LEARNING_ITEM_SET_EUROPEAN);
        asianLearningItemRecordStore.writeAll(Arrays.stream(asianLearningItems).map(LearningItem::toDisplayString).collect(Collectors.toList()));
        euroLearningItemRecordStore.writeAll(Arrays.stream(europeanLearningItems).map(LearningItem::toDisplayString).collect(Collectors.toList()));
    }

    @After
    public void afterEach() {
        guiTestWrapper.afterEach();
        ArrayList<LearningItem> toCleanUp = new ArrayList<>();
        toCleanUp.addAll(Arrays.asList(asianLearningItems));
        toCleanUp.addAll(Arrays.asList(europeanLearningItems));
        toCleanUp.add(deletedLearningItem);
        toCleanUp.add(replacementLearningItem);
        toCleanUp.add(writtenLearningItem);
        for (LearningItem learningItem : toCleanUp) {
            asianLearningItemRecordStore.delete(learningItem.toDisplayString());
            euroLearningItemRecordStore.delete(learningItem.toDisplayString());
        }
    }

    @Test
    public void itReadsOnlyFromTheAssociatedSet() {
        assertThat(asianLearningItemRecordStore.items(), allOf(hasItems(asianLearningItems), not(hasItems(europeanLearningItems))));
    }

    @Test
    public void itWritesALearningItemOnlyInTheAssociatedSet() {
        LearningItemText additionalLearningItem = writtenLearningItem.toDisplayString();

        euroLearningItemRecordStore.write(additionalLearningItem);

        assertThat(asianLearningItemRecordStore.items(), allOf(hasItems(asianLearningItems), not(hasItems(europeanLearningItems))));
        assertThat(euroLearningItemRecordStore.items(), allOf(hasItems(europeanLearningItems), not(hasItems(asianLearningItems)),
                hasItems(additionalLearningItem.withSet(LEARNING_ITEM_SET_EUROPEAN))));
    }

    @Test
    public void itReplacesALearningItemOnlyInTheAssociatedSet() {
        LearningItem replaced = deletedLearningItem;
        LearningItem replacement = replacementLearningItem;
        asianLearningItemRecordStore.write(replaced.toDisplayString());
        euroLearningItemRecordStore.write(replaced.toDisplayString());

        euroLearningItemRecordStore.replace(replaced.toDisplayString(), replacement.toDisplayString());

        assertThat(asianLearningItemRecordStore.items(), hasItem(replaced));
        assertThat(euroLearningItemRecordStore.items(), hasItem(replacement));
    }

    @Test
    public void itDeletesALearningItemOnlyInTheAssociatedSet() {
        LearningItemText deleted = deletedLearningItem.toDisplayString();
        asianLearningItemRecordStore.write(deleted);
        euroLearningItemRecordStore.write(deleted);

        asianLearningItemRecordStore.delete(deleted);

        assertThat(asianLearningItemRecordStore.items(), not(hasItem(deleted.withSet(LEARNING_ITEM_SET_DEFAULT))));
        assertThat(euroLearningItemRecordStore.items(), hasItem(deleted.withSet(LEARNING_ITEM_SET_EUROPEAN)));
    }

    @Test
    public void itDeletesAllLearningItemsOnlyInTheAssociatedSet() {
        euroLearningItemRecordStore.deleteAll();

        assertThat(euroLearningItemRecordStore.items(), empty());
        assertThat(asianLearningItemRecordStore.items(), hasItems(asianLearningItems));
    }
}