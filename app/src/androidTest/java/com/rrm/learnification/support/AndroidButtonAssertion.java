package com.rrm.learnification.support;

import android.app.Activity;
import android.widget.Button;

import static org.junit.Assert.assertEquals;

public class AndroidButtonAssertion {
    public static void assertButtonHasColour(Activity activity, int viewId, int expectedColor) {
        assertEquals("The view was not tagged with the expected colour",
                expectedColor, activity.<Button>findViewById(viewId).getTag());
    }
}
