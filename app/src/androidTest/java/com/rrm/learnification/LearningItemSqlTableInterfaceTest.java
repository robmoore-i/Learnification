package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.ItemStorage;
import com.rrm.learnification.storage.LearnificationAppDatabase;
import com.rrm.learnification.storage.LearningItemSqlTableInterface;
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

public class LearningItemSqlTableInterfaceTest {
    private static final String LEARNING_ITEM_SET_DEFAULT = "default";
    private static final String LEARNING_ITEM_SET_EUROPEAN = "european";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private ItemStorage<LearningItem> learningItemStorage;
    private List<LearningItem> originalLearningItems;

    private LearnificationAppDatabase learnificationAppDatabase;

    private LearningItemSqlTableInterface learningItemSqlTableInterfaceEuropean;
    private LearningItemSqlTableInterface learningItemSqlTableInterfaceAsian;

    @Before
    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        learningItemStorage = androidTestObjectFactory.getLearningItemStorage();
        originalLearningItems = learningItemStorage.read();
        learningItemStorage.rewrite(new ArrayList<>());

        learnificationAppDatabase = androidTestObjectFactory.getLearnificationAppDatabase();
        learningItemSqlTableInterfaceAsian = new LearningItemSqlTableInterface(LEARNING_ITEM_SET_DEFAULT);
        learningItemSqlTableInterfaceEuropean = new LearningItemSqlTableInterface(LEARNING_ITEM_SET_EUROPEAN);

        learningItemSqlTableInterfaceAsian.writeAll(learnificationAppDatabase.getWritableDatabase(), Arrays.asList(asianLearningItems()));
        learningItemSqlTableInterfaceEuropean.writeAll(learnificationAppDatabase.getWritableDatabase(), Arrays.asList(europeanLearningItems()));
    }

    @After
    public void afterEach() {
        learningItemStorage.rewrite(originalLearningItems);
    }

    @Test
    public void itReadsOnlyFromTheAssociatedSet() {
        assertThat(learningItemSqlTableInterfaceAsian.readAll(learnificationAppDatabase.getReadableDatabase()),
                allOf(hasItems(asianLearningItems()), not(hasItems(europeanLearningItems()))));
    }

    @Test
    public void itWritesALearningItemOnlyInTheAssociatedSet() {
        LearningItem additionalLearningItem = new LearningItem("l-test-write", "r-test-write");
        learningItemSqlTableInterfaceEuropean.write(learnificationAppDatabase.getWritableDatabase(), additionalLearningItem);

        assertThat(learningItemSqlTableInterfaceAsian.readAll(learnificationAppDatabase.getReadableDatabase()),
                allOf(hasItems(asianLearningItems()), not(hasItems(europeanLearningItems()))));
        assertThat(learningItemSqlTableInterfaceEuropean.readAll(learnificationAppDatabase.getReadableDatabase()),
                allOf(hasItems(europeanLearningItems()), hasItems(additionalLearningItem), not(hasItems(asianLearningItems()))));
    }

    @Test
    public void itReplacesALearningItemOnlyInTheAssociatedSet() {
        LearningItem replaced = new LearningItem("l-test-replace", "r-test-replace");
        LearningItem replacement = new LearningItem("l-test-replacement", "r-test-replacement");
        learningItemSqlTableInterfaceAsian.write(learnificationAppDatabase.getWritableDatabase(), replaced);
        learningItemSqlTableInterfaceEuropean.write(learnificationAppDatabase.getWritableDatabase(), replaced);

        learningItemSqlTableInterfaceEuropean.replace(learnificationAppDatabase.getWritableDatabase(), replaced, replacement);

        assertThat(learningItemSqlTableInterfaceAsian.readAll(learnificationAppDatabase.getReadableDatabase()), hasItem(replaced));
        assertThat(learningItemSqlTableInterfaceEuropean.readAll(learnificationAppDatabase.getReadableDatabase()), hasItem(replacement));
    }

    @Test
    public void itDeletesALearningItemOnlyInTheAssociatedSet() {
        LearningItem deleted = new LearningItem("l-test-replace", "r-test-replace");
        learningItemSqlTableInterfaceAsian.write(learnificationAppDatabase.getWritableDatabase(), deleted);
        learningItemSqlTableInterfaceEuropean.write(learnificationAppDatabase.getWritableDatabase(), deleted);

        learningItemSqlTableInterfaceAsian.delete(learnificationAppDatabase.getWritableDatabase(), deleted);

        assertThat(learningItemSqlTableInterfaceAsian.readAll(learnificationAppDatabase.getReadableDatabase()), not(hasItem(deleted)));
        assertThat(learningItemSqlTableInterfaceEuropean.readAll(learnificationAppDatabase.getReadableDatabase()), hasItem(deleted));
    }

    @Test
    public void itDeletesAllLearningItemsOnlyInTheAssociatedSet() {
        learningItemSqlTableInterfaceEuropean.deleteAll(learnificationAppDatabase.getWritableDatabase());

        assertThat(learningItemSqlTableInterfaceEuropean.readAll(learnificationAppDatabase.getReadableDatabase()), empty());
        assertThat(learningItemSqlTableInterfaceAsian.readAll(learnificationAppDatabase.getReadableDatabase()), hasItems(asianLearningItems()));
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