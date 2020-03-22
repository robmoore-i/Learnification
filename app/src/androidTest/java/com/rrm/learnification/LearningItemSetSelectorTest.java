package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.storage.LearningItemSqlTableClient;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

public class LearningItemSetSelectorTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    private DatabaseTestWrapper databaseTestWrapper;
    private LearningItemSqlTableClient learningItemSqlTableClient;
    private SqlLearningItemSetRecordStore sqlLearningItemSetRecordStore;

    @Before
    public void beforeEach() {
        LearningItemSetEditorActivity activity = activityTestRule.getActivity();
        databaseTestWrapper = new DatabaseTestWrapper(activity);
        databaseTestWrapper.beforeEach();
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activity);
        learningItemSqlTableClient = androidTestObjectFactory.getLearningItemSqlTableClient();
        learningItemSqlTableClient.writeAll(Collections.singletonList(new LearningItem("a", "b", "default")));
        sqlLearningItemSetRecordStore = androidTestObjectFactory.getDefaultSqlLearningItemSetRecordStore();
    }

    @After
    public void afterEach() {
        databaseTestWrapper.afterEach();
    }

    @Test
    public void whenTheCurrentLearningItemIsRenamedTheSelectorShowsTheUpdatedName() {
        String updatedLearningItemSetName = "Thai";
        sqlLearningItemSetRecordStore.renameSet(updatedLearningItemSetName);

        int spinnerId = R.id.learning_item_set_selector;
        onView(withId(spinnerId)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(updatedLearningItemSetName))).perform(click());
        onView(withId(spinnerId)).check(matches(withSpinnerText(containsString(updatedLearningItemSetName))));
    }
}
