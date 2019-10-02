package com.rrm.learnification.common;

public class LearnificationText {
    public final String given;
    public final String expected;
    public final String subHeading;

    LearnificationText(String given, String expected, String subHeading) {
        this.expected = expected;
        this.given = given;
        this.subHeading = subHeading;
    }
}