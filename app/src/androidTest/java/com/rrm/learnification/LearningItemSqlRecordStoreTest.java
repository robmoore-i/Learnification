package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSqlRecordStore;
import com.rrm.learnification.storage.PersistentItemStore;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class LearningItemSqlRecordStoreTest {
    private static final String LEARNING_ITEM_SET_DEFAULT = "default";
    private static final String LEARNING_ITEM_SET_EUROPEAN = "european";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private PersistentItemStore<LearningItem> learningPersistentItemStore;
    private List<LearningItem> originalLearningItems;

    private LearnificationAppDatabase learnificationAppDatabase;

    private LearningItemSqlRecordStore learningItemSqlTableInterfaceEuropean;
    private LearningItemSqlRecordStore learningItemSqlTableInterfaceAsian;

    @Before
    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        learningPersistentItemStore = androidTestObjectFactory.getLearningItemStorage();
        originalLearningItems = learningPersistentItemStore.read();
        learningPersistentItemStore.rewrite(new ArrayList<>());

        learnificationAppDatabase = androidTestObjectFactory.getLearnificationAppDatabase();
        learningItemSqlTableInterfaceAsian = new LearningItemSqlRecordStore(androidTestObjectFactory.getLearnificationAppDatabase(), LEARNING_ITEM_SET_DEFAULT);
        learningItemSqlTableInterfaceEuropean = new LearningItemSqlRecordStore(androidTestObjectFactory.getLearnificationAppDatabase(), LEARNING_ITEM_SET_EUROPEAN);

        learningItemSqlTableInterfaceAsian.writeAll(Arrays.asList(asianLearningItems()));
        learningItemSqlTableInterfaceEuropean.writeAll(Arrays.asList(europeanLearningItems()));
    }

    @After
    public void afterEach() {
        learningPersistentItemStore.rewrite(originalLearningItems);
    }

    @Test
    public void itReadsOnlyFromTheAssociatedSet() {
        assertThat(learningItemSqlTableInterfaceAsian.readAll(),
                allOf(hasItems(asianLearningItems()), not(hasItems(europeanLearningItems()))));
    }

    @Test
    public void itWritesALearningItemOnlyInTheAssociatedSet() {
        LearningItem additionalLearningItem = new LearningItem("l-test-write", "r-test-write");
        learningItemSqlTableInterfaceEuropean.write(additionalLearningItem);

        assertThat(learningItemSqlTableInterfaceAsian.readAll(),
                allOf(hasItems(asianLearningItems()), not(hasItems(europeanLearningItems()))));
        assertThat(learningItemSqlTableInterfaceEuropean.readAll(),
                allOf(hasItems(europeanLearningItems()), hasItems(additionalLearningItem), not(hasItems(asianLearningItems()))));
    }

    @Test
    public void itReplacesALearningItemOnlyInTheAssociatedSet() {
        LearningItem replaced = new LearningItem("l-test-replace", "r-test-replace");
        LearningItem replacement = new LearningItem("l-test-replacement", "r-test-replacement");
        learningItemSqlTableInterfaceAsian.write(replaced);
        learningItemSqlTableInterfaceEuropean.write(replaced);

        learningItemSqlTableInterfaceEuropean.replace(replaced, replacement);

        assertThat(learningItemSqlTableInterfaceAsian.readAll(), hasItem(replaced));
        assertThat(learningItemSqlTableInterfaceEuropean.readAll(), hasItem(replacement));
    }

    @Test
    public void itDeletesALearningItemOnlyInTheAssociatedSet() {
        LearningItem deleted = new LearningItem("l-test-replace", "r-test-replace");
        learningItemSqlTableInterfaceAsian.write(deleted);
        learningItemSqlTableInterfaceEuropean.write(deleted);

        learningItemSqlTableInterfaceAsian.delete(deleted);

        assertThat(learningItemSqlTableInterfaceAsian.readAll(), not(hasItem(deleted)));
        assertThat(learningItemSqlTableInterfaceEuropean.readAll(), hasItem(deleted));
    }

    @Test
    public void itDeletesAllLearningItemsOnlyInTheAssociatedSet() {
        learningItemSqlTableInterfaceEuropean.deleteAll();

        assertThat(learningItemSqlTableInterfaceEuropean.readAll(), empty());
        assertThat(learningItemSqlTableInterfaceAsian.readAll(), hasItems(asianLearningItems()));
    }

    private LearningItem[] asianLearningItems() {
        ArrayList<LearningItem> learningItemsAsian = new ArrayList<>();
        learningItemsAsian.add(new LearningItem("frog", "gemba"));
        learningItemsAsian.add(new LearningItem("phone", "machine brain"));
        learningItemsAsian.add(new LearningItem("good", "dee"));
        return learningItemsAsian.toArray(new LearningItem[0]);
    }

    private LearningItem[] europeanLearningItems() {
        ArrayList<LearningItem> learningItemsEuropean = new ArrayList<>();
        learningItemsEuropean.add(new LearningItem("der", "the"));
        learningItemsEuropean.add(new LearningItem("gamarjoba", "hello"));
        learningItemsEuropean.add(new LearningItem("sans", "without"));
        return learningItemsEuropean.toArray(new LearningItem[0]);
    }

}