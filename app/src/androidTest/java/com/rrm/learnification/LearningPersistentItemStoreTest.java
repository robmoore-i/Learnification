package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    private SqlLearningItemSetRecordStore learningItemStorage;
    private DatabaseTestWrapper databaseTestWrapper;

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        databaseTestWrapper = new DatabaseTestWrapper(activity);
        databaseTestWrapper.beforeEach();
        learningItemStorage = new AndroidTestObjectFactory(activity).getDefaultSqlLearningItemSetRecordStore();
    }

    @After
    public void afterEach() {
        databaseTestWrapper.afterEach();
    }

    @Test
    public void canWriteThenReadLearningItem() {
        learningItemStorage.write(a);

        List<LearningItem> learningItems = learningItemStorage.readAll();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
    }

    @Test
    public void canWriteTwoLearningItemsThenReadThem() {
        learningItemStorage.write(a);
        learningItemStorage.write(b);

        List<LearningItem> learningItems = learningItemStorage.readAll();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));

        learningItem = learningItems.get(1);
        assertThat(learningItem, equalTo(b));
    }

    @Test
    public void canReadThenWriteAnotherLearningItem() {
        learningItemStorage.readAll();
        learningItemStorage.write(a);

        List<LearningItem> learningItems = learningItemStorage.readAll();

        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
    }

    @Test
    public void canWriteThenReadThenDeleteThenReadToCheckItsGoneForASingleLearningItem() {
        learningItemStorage.write(a);
        List<LearningItem> learningItems = learningItemStorage.readAll();

        assertThat(learningItems.size(), equalTo(1));
        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));

        learningItemStorage.delete(learningItems.get(0));
        learningItems = learningItemStorage.readAll();
        assertThat(learningItems.size(), equalTo(0));
    }

    @Test
    public void canWriteTwoLearningItemsThenReadThemThenDeleteTheSecondThenReadAgain() {
        learningItemStorage.write(a);
        learningItemStorage.write(b);
        List<LearningItem> learningItems = learningItemStorage.readAll();

        assertThat(learningItems.size(), equalTo(2));
        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
        learningItem = learningItems.get(1);
        assertThat(learningItem, equalTo(b));

        learningItemStorage.delete(learningItems.get(1));
        learningItems = learningItemStorage.readAll();
        assertThat(learningItems.size(), equalTo(1));
        learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
    }

    @Test
    public void canWriteTwoLearningItemsThenReadThemThenDeleteTheFirstThenReadAgain() {
        learningItemStorage.write(a);
        learningItemStorage.write(b);
        List<LearningItem> learningItems = learningItemStorage.readAll();

        assertThat(learningItems.size(), equalTo(2));
        LearningItem learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(a));
        learningItem = learningItems.get(1);
        assertThat(learningItem, equalTo(b));

        learningItemStorage.delete(learningItems.get(0));
        learningItems = learningItemStorage.readAll();
        assertThat(learningItems.size(), equalTo(1));
        learningItem = learningItems.get(0);
        assertThat(learningItem, equalTo(b));
    }

    @Test
    public void canReplaceALearningItem() {
        learningItemStorage.write(a);
        learningItemStorage.write(b);
        learningItemStorage.write(c);

        learningItemStorage.replace(b, bMod);
        List<LearningItem> learningItems = learningItemStorage.readAll();
        LearningItem learningItem = learningItems.get(1);
        assertThat(learningItem, equalTo(bMod));
    }
}
