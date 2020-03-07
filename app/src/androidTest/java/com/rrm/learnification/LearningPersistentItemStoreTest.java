package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.storage.PersistentItemStore;

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
public class LearningPersistentItemStoreTest {
    private final LearningItem a = new LearningItem("sql", "lite");
    private final LearningItem b = new LearningItem("from", "file");
    private final LearningItem bMod = new LearningItem("from", "mars");
    private final LearningItem c = new LearningItem("vanity", "fair");

    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private PersistentItemStore<LearningItem> learningPersistentItemStore;
    private List<LearningItem> originalLearningItems;

    @Before
    public void beforeEach() {
        learningPersistentItemStore = activityTestRule.getActivity().androidTestObjectFactory().getLearningItemStorage();
        originalLearningItems = learningPersistentItemStore.read();
        learningPersistentItemStore.rewrite(new ArrayList<>());

    }

    @After
    public void afterEach() {
        learningPersistentItemStore.rewrite(originalLearningItems);
    }

    @Test
    public void canWriteThenReadLearningItem() {
        learningPersistentItemStore.write(a);

        List<LearningItem> learningItems = learningPersistentItemStore.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
    }

    @Test
    public void canWriteTwoLearningItemsThenReadThem() {
        learningPersistentItemStore.write(a);
        learningPersistentItemStore.write(b);

        List<LearningItem> learningItems = learningPersistentItemStore.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));

        learningItem = learningItems.get(1);
        assertThat(learningItem, equalTo(b));
    }

    @Test
    public void canReadThenWriteAnotherLearningItem() {
        learningPersistentItemStore.read();
        learningPersistentItemStore.write(a);

        List<LearningItem> learningItems = learningPersistentItemStore.read();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
    }

    @Test
    public void canWriteThenReadThenDeleteThenReadToCheckItsGoneForASingleLearningItem() {
        learningPersistentItemStore.write(a);
        List<LearningItem> learningItems = learningPersistentItemStore.read();

        assertThat(learningItems.size(), equalTo(1));
        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));

        learningPersistentItemStore.remove(learningItems.get(0));
        learningItems = learningPersistentItemStore.read();
        assertThat(learningItems.size(), equalTo(0));
    }

    @Test
    public void canWriteTwoLearningItemsThenReadThemThenDeleteTheSecondThenReadAgain() {
        learningPersistentItemStore.write(a);
        learningPersistentItemStore.write(b);
        List<LearningItem> learningItems = learningPersistentItemStore.read();

        assertThat(learningItems.size(), equalTo(2));
        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
        learningItem = learningItems.get(1);
        assertThat(learningItem, equalTo(b));

        learningPersistentItemStore.remove(learningItems.get(1));
        learningItems = learningPersistentItemStore.read();
        assertThat(learningItems.size(), equalTo(1));
        learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
    }

    @Test
    public void canWriteTwoLearningItemsThenReadThemThenDeleteTheFirstThenReadAgain() {
        learningPersistentItemStore.write(a);
        learningPersistentItemStore.write(b);
        List<LearningItem> learningItems = learningPersistentItemStore.read();

        assertThat(learningItems.size(), equalTo(2));
        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
        learningItem = learningItems.get(1);
        assertThat(learningItem, equalTo(b));

        learningPersistentItemStore.remove(learningItems.get(0));
        learningItems = learningPersistentItemStore.read();
        assertThat(learningItems.size(), equalTo(1));
        learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(b));
    }

    @Test
    public void canReplaceALearningItem() {
        learningPersistentItemStore.write(a);
        learningPersistentItemStore.write(b);
        learningPersistentItemStore.write(c);

        learningPersistentItemStore.replace(b, bMod);
        List<LearningItem> learningItems = learningPersistentItemStore.read();
        LearningItem learningItem = learningItems.get(1);
        assertThat(learningItem, equalTo(bMod));
    }
}
