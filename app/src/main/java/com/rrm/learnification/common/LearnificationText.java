package com.rrm.learnification.common;

public class LearnificationText {
    public final String given;
    public final String expected;
    public final String subHeading;

    public LearnificationText(String given, String expected) {
        this.expected = expected;
        this.given = given;
        this.subHeading = "Learn!";
    }
}
