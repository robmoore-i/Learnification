package com.rrm.learnification.support;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;

import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assume.assumeThat;

public class LearnificationAppAssumption {
    public static void assumeThatThereAreAnyLearningItems(ActivityTestRule<LearningItemSetEditorActivity> activityTestRule) {
        List<LearningItem> learningItems = activityTestRule.getActivity().androidTestObjectFactory().getLearningItemSqlTableClient().items();
        assumeThat(learningItems, not(empty()));
    }

}