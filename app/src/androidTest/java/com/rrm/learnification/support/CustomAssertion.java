package com.rrm.learnification.support;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.rrm.learnification.R;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class CustomAssertion {
    public static void assertButtonHasColour(Activity activity, int viewId, int expectedColor) {
        assertEquals("The view was not tagged with the expected colour",
                expectedColor, activity.<Button>findViewById(viewId).getTag());
    }

    public static void assertLearningItemListHasSize(Activity activity, int expectedNumberOfElements) {
        assertThat(activity
                        .<RecyclerView>findViewById(R.id.learning_item_list)
                        .getChildCount(),
                equalTo(expectedNumberOfElements));
    }
}
