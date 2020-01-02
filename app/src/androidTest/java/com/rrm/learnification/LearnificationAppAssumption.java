package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.main.MainActivity;

import static org.junit.Assume.assumeFalse;

class LearnificationAppAssumption {
    static void assumeThatThereAreLearningItems(ActivityTestRule<MainActivity> activityTestRule) {
        assumeFalse(learningItemsAreEmpty(activityTestRule));
    }

    private static boolean learningItemsAreEmpty(ActivityTestRule<MainActivity> activityTestRule) {
        return activityTestRule.getActivity().getLearningItemStorage().read().isEmpty();
    }
}
