package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;

import static org.junit.Assume.assumeFalse;

class LearnificationAppAssumption {
    static void assumeThatThereAreLearningItems(ActivityTestRule<LearningItemSetEditorActivity> activityTestRule) {
        assumeFalse(learningItemsAreEmpty(activityTestRule));
    }

    private static boolean learningItemsAreEmpty(ActivityTestRule<LearningItemSetEditorActivity> activityTestRule) {
        return activityTestRule.getActivity().androidTestObjectFactory().getLearningItemStorage().read().isEmpty();
    }
}
