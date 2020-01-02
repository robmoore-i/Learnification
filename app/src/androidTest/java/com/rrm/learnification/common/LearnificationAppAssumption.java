package com.rrm.learnification.common;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.main.MainActivity;

import static org.junit.Assume.assumeFalse;

class LearnificationAppAssumption {
    static void assumeThatThereAreLearningItems(ActivityTestRule<MainActivity> activityTestRule) {
        assumeFalse(learningItemsAreEmpty(activityTestRule));
    }

    static boolean learningItemsAreEmpty(ActivityTestRule<MainActivity> activityTestRule) {
        return activityTestRule.getActivity().getLearningItemStorage().read().isEmpty();
    }
}
