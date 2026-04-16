package com.rrm.learnification.support;

import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

class CustomMatcher {
    static Matcher<View> withToolbarTitle(final Matcher<String> textMatcher) {
        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
